package com.qunar.work.toutiao;

import com.clearspring.analytics.util.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by zhipengwu on 17-7-27. 将libsvm格式数据转换为csv
 */
public class LibsvmToCSV {
    public static Logger logger = LoggerFactory.getLogger(LibsvmToCSV.class);

    public static Map<String, Integer> featurNametoIndexMap;
    //计算分类特征出现频次最高的条目
    public static Map<String,Integer> countCategoryFeatureMap=Maps.newHashMap();

    public static void main(String[] args) throws IOException {
//        String baseDir = "/home/zhipengwu/Innovation/BigBang/JavaTutorial/src/main/resources/";
//        // 输入文件名称参数
//        String inputFileName = "hotel_train_20170813.libsvm";
//        String libsvmDescFile = baseDir + "libsvm格式说明20170813.txt"; // libsvm列名文件

        String baseDir = args[0];
        // 输入文件名称参数
        String inputFileName = args[1];
        String libsvmDescFile = baseDir + args[2]; // libsvm列名文件


        String outputFileName = baseDir+inputFileName + ".csv";
        String inputFile = baseDir + inputFileName;
        String outputFile = baseDir + outputFileName;
        int dim = 29; // 转换后的维数
        FileWriter csvFile = new FileWriter(outputFile);
        Map<Integer, LibsvmDes> libsvmDesMap = loadLibsvmDes(libsvmDescFile);
        // csv文件中列名到列位置的映射
        featurNametoIndexMap = createfeaturNametoIndexMap(libsvmDescFile);

        // ===========输出csv文件各列名================
        FileWriter csv_colum_name_desc = new FileWriter(outputFile + ".desc");
        for (String key : featurNametoIndexMap.keySet()) {
            System.out.println(String.format("%s\t%s", key, featurNametoIndexMap.get(key)));
            csv_colum_name_desc.append(String.format("%s\t%s\n", key, featurNametoIndexMap.get(key)));
        }
        csv_colum_name_desc.flush();
        csv_colum_name_desc.close();


        LineIterator lineIterator = FileUtils.lineIterator(new File(inputFile));
        while (lineIterator.hasNext()) {
            String line = lineIterator.nextLine();
            List<String> rowList = parseLine(line, libsvmDesMap, featurNametoIndexMap, dim);
            // ===========生成gbdt的输入特征==========
            rowList = createFeartureforGBDT(rowList);

            String csvrow = rowList.toString();

            csvrow = csvrow.substring(1, csvrow.length() - 1).replaceAll(" ", "");
            if (csvrow.endsWith(",")) {
                csvrow = csvrow.substring(0, csvrow.length() - 1);
            }
            // csvrow=csvrow.replaceAll("\\[","");
            // csvrow=csvrow.replaceAll("\\]","");
            // csvrow=csvrow.replaceAll("\\(","");
            // csvrow=csvrow.replaceAll("\\)","");
            csvFile.append(csvrow + "\n");
            // System.out.println(rowList.toString());
        }
        csvFile.flush();
        csvFile.close();
        lineIterator.close();

        //获取分类特征中出现次数较高的值
        Map<String, Integer> stringIntegerMap = sortByValue(countCategoryFeatureMap);
       for (String key:stringIntegerMap.keySet()){
           System.out.println(key+"\t"+stringIntegerMap.get(key));
       }

    }

    public static List<String> parseLine(String line, Map<Integer, LibsvmDes> libsvmDesMap,
            Map<String, Integer> featurNametoIndexMap, int dim) {
        List<List<String>> rowslist = Lists.newArrayList();
        List<String> rowList = Lists.newArrayList();
        if (Strings.isNullOrEmpty(line)) {
            return null;
        }
        for (int i = 0; i < dim + 1; i++) {
            rowList.add("");
        }

        String[] split = line.split(" ");
        //
        for (String item : split) {
            int indexcolon = item.indexOf(":");
            if (indexcolon < 0) { // 第一列
                rowList.set(0, item);
                continue;
            }
            int index = Integer.valueOf(item.substring(0, indexcolon));

            Double value = Double.valueOf(item.substring(indexcolon + 1));
            if (value > 0 && index >= 0) {
                LibsvmDes libsvmDes = libsvmDesMap.get(index);
                if (libsvmDes != null && featurNametoIndexMap.containsKey(libsvmDes.featureName)) {
                    Integer featureIndex = featurNametoIndexMap.get(libsvmDes.featureName);
                    String pre_s = rowList.get(featureIndex);
                    if (Strings.isNullOrEmpty(pre_s)) {
                        rowList.set(featureIndex, String.format("%s", libsvmDes.featureValue.replaceAll(", ", "-")));
                    } else {
                        rowList.set(featureIndex,
                                String.format("%s#%s", pre_s, libsvmDes.featureValue.replaceAll(", ", "-")));
                    }
                }

            }
        }

        return rowList;
    }

    // 建立csv列名到列位置的映射
    public static Map<String, Integer> createfeaturNametoIndexMap(String libsvmDesFile) {
        Map<String, Integer> featurNametoIndexMap = Maps.newHashMap();
        int count = 1;
        try {
            LineIterator lineIterator = FileUtils.lineIterator(new File(libsvmDesFile));
            while (lineIterator.hasNext()) {
                String line = lineIterator.nextLine();
                String[] split = line.split("\t");
                if (split == null || split.length != 2) {
                    continue;
                }
                String desc = split[1];
                int indexBrace = desc.indexOf("[");
                if (indexBrace > 0) {
                    String featureName = desc.substring(0, indexBrace);
                    if (!Strings.isNullOrEmpty(featureName) && !featurNametoIndexMap.containsKey(featureName)) {
                        featurNametoIndexMap.put(featureName, count);
                        count++;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("加载文件失败" + libsvmDesFile);

        }
        return featurNametoIndexMap;

    }

    // 加载libsvm描述文件到map
    public static Map<Integer, LibsvmDes> loadLibsvmDes(String file) {
        Map<Integer, LibsvmDes> libsvmDesMap = Maps.newHashMap();

        try {
            LineIterator lineIterator = FileUtils.lineIterator(new File(file));
            while (lineIterator.hasNext()) {
                String line = lineIterator.nextLine();
                String[] split = line.split("\t");
                if (split == null || split.length != 2) {
                    continue;
                }
                int index = Integer.valueOf(split[0]);
                String desc = split[1];
                if (desc.contains("*")) {
                    continue;
                }
                String featureName = "";
                String featureValue = "";
                int indexBrace = desc.indexOf("[");
                if (indexBrace > 0) {
                    featureName = desc.substring(0, indexBrace);
                    featureValue = desc.substring(indexBrace);
                    libsvmDesMap.put(index, new LibsvmDes(index, featureName, featureValue));
                }
            }
        } catch (IOException e) {
            logger.error("加载文件失败" + file);
            e.printStackTrace();
        }
        return libsvmDesMap;

    }

    public static class LibsvmDes {
        public LibsvmDes() {
        }

        public LibsvmDes(int index, String featureName, String featureValue) {
            this.featureName = featureName;
            this.featureValue = featureValue;
            this.index = index;
        }

        public String featureName;
        public String featureValue;
        public int index;
    }

    // 为gbdt输入构建特征
    public static List<String> createFeartureforGBDT(List<String> rowList) throws IOException {
        DecimalFormat dcmFmt = new DecimalFormat("0.00");
        //platform, model, allCity 三列移到list的末尾
        Integer platformIndex = featurNametoIndexMap.get("platform");
        Integer modelIndex = featurNametoIndexMap.get("model");
        Integer allCityIndex = featurNametoIndexMap.get("allCity");
        String platform=rowList.get(platformIndex);
        String model= rowList.get(modelIndex);
        String allCity=rowList.get(allCityIndex);
        String formate_model="model-"+ rowList.get(modelIndex);
        String formate_allCity="allCity-"+rowList.get(allCityIndex);
        if(countCategoryFeatureMap.containsKey(formate_model)){
            Integer integer = countCategoryFeatureMap.get(formate_model);
            integer++;
            countCategoryFeatureMap.put(formate_model,integer);
        }else {
            countCategoryFeatureMap.put(formate_model,1);
        }

        if(countCategoryFeatureMap.containsKey(formate_allCity)){
            Integer integer = countCategoryFeatureMap.get(formate_allCity);
            integer+=1;
            countCategoryFeatureMap.put(formate_allCity,integer);
        }else {
            countCategoryFeatureMap.put(formate_allCity,1);
        }


        for (int i = 0; i < rowList.size(); i++) {
            String s = rowList.get(i);
            String value = "-10";
            if (Strings.isNullOrEmpty(s)) {
                continue;
            }
            if (s.contains("[") && (s.contains("]") || s.contains(")"))) {
                s = s.substring(1, s.length() - 1);
            }
            // 处理范围类型的特征
            if (s.contains("-")) {
                String[] split = s.split("-");
                if (split.length == 2 && isNum(split[0])) {
                    if (split[1].equalsIgnoreCase(":")) {
                        value = split[0];
                    } else if (isNum(split[1])) {
                        String format = dcmFmt.format((Double.valueOf(split[1]) + Double.valueOf(split[0])) / 2);
                        if (Double.valueOf(format)>20000){
                            format="-10";
                        }
                        value = String
                                .valueOf(format);
                    }
                }
            }
            // 处理性别特征
            if (s.equalsIgnoreCase("F")) {
                value = "2";
            }
            if (s.equalsIgnoreCase("M")) {
                value = "1";
            }
            if (s.equalsIgnoreCase("X")) {
                value = "0";
            }

            // 处理数字特征
            if (isNum(s)) {
                if (Double.valueOf(s)>200000){
                    s="-10";
                }
                value = s;
            }
            rowList.set(i, value);
        }

//        rowList.remove(platformIndex);
//        rowList.remove(modelIndex);
//        rowList.remove(allCityIndex);

        rowList.add(platform);
        rowList.add(model);
        rowList.add(allCity);
        return rowList;
    }

    public static boolean isNum(String str) {

        try {
            new BigDecimal(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K, V>> st = map.entrySet().stream();

        st.sorted(Comparator.comparing(e -> e.getValue())).forEach(e -> result.put(e.getKey(), e.getValue()));

        return result;
    }

}
