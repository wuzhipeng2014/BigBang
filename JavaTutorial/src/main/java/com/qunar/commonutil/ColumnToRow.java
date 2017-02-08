package com.qunar.commonutil;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * 将一行数据才分成多行
 * Created by zhipengwu on 16-12-1.
 */
public class ColumnToRow {

   public static Joiner joiner = Joiner.on("\t").skipNulls();


    public static void main(String[] args) {
//        List<String> strings = FileUtil.readFileToList("hotel_type.txt");
//        String line =strings.get(1);
//        String[] split1 = line.split("\t");
//        String[] split = split1[0].split("\u0002");
//        int len=split.length;

        rowSpliter("hotel_type_201607_statistic.txt","splithotel_type_201607_statistic.txt");



//        System.out.println(strings.get(1));

    }


    public static void rowSpliter(String inputFileName, String outputFileName){
        Preconditions.checkNotNull(inputFileName,"输入文件名不能为空");
        Preconditions.checkNotNull(outputFileName,"输出文件名不能为空");
        List<String> strings = FileUtil.readFileToList(inputFileName);
        List<String> resultList=Lists.newArrayList();
        for (String line:strings){
            List<String> stringList = rowHandler(line,2,"\t","\u0002");
            resultList.addAll(stringList);
        }
        FileUtil.writeFile(resultList,outputFileName);


    }

    /**
     *
     * @param line 待分割的行
     * @param splitColumnIndex 待分割的列index 从0开始
     * @param columnSeprator 列分割符
     * @param subColumnSeprator 列转行分隔符
     * @return
     */
    public static List<String> rowHandler(String line, int splitColumnIndex, String columnSeprator, String subColumnSeprator){
        List<String> combinedRows= Lists.newArrayList();
        if (line!=null){
            String[] columns = line.split(columnSeprator);
            int len=columns.length;
            if (len>splitColumnIndex){
                String[] rows = columns[splitColumnIndex].split(subColumnSeprator);
                for (String subRow:rows){
                    columns[splitColumnIndex]=subRow;
                    String combiningLine=joiner.join(columns);
                    combinedRows.add(combiningLine);
                }
            }

        }
        return combinedRows;
    }

}
