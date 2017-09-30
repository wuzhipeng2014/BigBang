package com.qunar.toutiao;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhipengwu on 17-9-14.
 */
public class MDLPDiscretization {
    public static void main(String[] args) {
        String inputFile="/home/zhipengwu/secureCRT/feature.csv";

        SparkConf conf = new SparkConf().setMaster("local").setAppName("wordCount");
        JavaSparkContext sc = new JavaSparkContext(conf);
        //从本地文件系统读取数据
//        JavaRDD<String> stringJavaRDD = sc.textFile("/home/zhipengwu/secureCRT/feature.csv");
        SQLContext sqlContext=new SQLContext(sc);

        Dataset<Row> load = sqlContext.read().format("csv").load(inputFile);
        List<String> age = Arrays.asList("age");

//        DiscretizerModel train = MDLPDiscretizer.train(load, age, 100, 10000, 0.1, 1.0);



        System.out.println();


    }
}
