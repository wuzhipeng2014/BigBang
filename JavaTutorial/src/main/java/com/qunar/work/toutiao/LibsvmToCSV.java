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
import java.util.List;
import java.util.Map;

/**
 * Created by zhipengwu on 17-7-27. 将libsvm格式数据转换为csv
 */
public class LibsvmToCSV {
    public static Logger logger = LoggerFactory.getLogger(LibsvmToCSV.class);

    public static void main(String[] args) throws IOException {
        String baseDir = "/home/zhipengwu/Innovation/BigBang/JavaTutorial/src/main/resources/";
        String inputFileName = "neg_toutiao_20170723_1wLine.txt";
        String outputFileName = inputFileName + ".csv";
        String inputFile = baseDir + inputFileName;
        String outputFile = baseDir + outputFileName;
        int dim = 30; // 转换后的维数

//        String inputFile = "/home/zhipengwu/Innovation/BigBang/JavaTutorial/src/main/resources/pos_traffic_20170723";
//        String outputFile = "/home/zhipengwu/Innovation/BigBang/JavaTutorial/src/main/resources/pos_traffic_20170723.csv";
        String libsvmDescFile = "/home/zhipengwu/Innovation/BigBang/JavaTutorial/src/main/resources/libsvm格式说明_20170727.txt"; // libsvm列名文件

        FileWriter csvFile = new FileWriter(outputFile);
        Map<Integer, LibsvmDes> libsvmDesMap = loadLibsvmDes(libsvmDescFile);
        //csv文件中列名到列位置的映射
        Map<String, Integer> featurNametoIndexMap = createfeaturNametoIndexMap(libsvmDescFile);
        //输出csv文件各列名
//        for (String key:featurNametoIndexMap.keySet()){
//            System.out.println(String.format("%s\t%s",key,featurNametoIndexMap.get(key)));
//        }

        LineIterator lineIterator = FileUtils.lineIterator(new File(inputFile));
        while (lineIterator.hasNext()) {
            String line = lineIterator.nextLine();
            List<String> rowList = parseLine(line, libsvmDesMap, featurNametoIndexMap, dim);
            String csvrow = rowList.toString();
            csvrow = csvrow.substring(1, csvrow.length() - 1).replaceAll(" ", "");
            if (csvrow.endsWith(",")){
                csvrow=csvrow.substring(0,csvrow.length()-1);
            }
            csvFile.append(csvrow + "\n");
            System.out.println(rowList.toString());
        }
        csvFile.flush();
        csvFile.close();
        lineIterator.close();

    }

    public static List<String> parseLine(String line, Map<Integer, LibsvmDes> libsvmDesMap, Map<String, Integer> featurNametoIndexMap, int dim) {
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
            if (indexcolon < 0) { //第一列
                rowList.set(0, item);
                continue;
            }
            int index = Integer.valueOf(item.substring(0, indexcolon));

            Double value = Double.valueOf(item.substring(indexcolon + 1));
            if (value > 0 && index >= 0) {
                LibsvmDes libsvmDes = libsvmDesMap.get(index);
                if (libsvmDes != null && featurNametoIndexMap.containsKey(libsvmDes.featureName)) {
                    Integer featureIndex = featurNametoIndexMap.get(libsvmDes.featureName);
                    rowList.set(featureIndex, libsvmDes.featureValue.replaceAll(", ", "-"));
                }

            }
        }

        return rowList;
    }


    //建立csv列名到列位置的映射
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

    //加载libsvm描述文件到map
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

}
