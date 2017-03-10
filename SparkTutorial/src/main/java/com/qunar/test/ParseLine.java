package com.qunar.test;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import scala.Tuple2;

import java.io.StringReader;
import java.util.Iterator;

/**
 * Created by zhipengwu on 17-3-3.
 * csv格式数据读取
 */
public class ParseLine implements FlatMapFunction<Tuple2<String,String>, String[]> {
    @Override
    public Iterator<String[]> call(Tuple2<String, String> stringStringTuple2) throws Exception {
        CSVReader csvReader = new CSVReader(new StringReader(stringStringTuple2._2()));
        return csvReader.readAll().iterator();
    }

    public static void main(String[] args) {
        String inputPath=args[0];
        if (inputPath==null||inputPath.length()<1){
            inputPath="";
        }
        SparkConf conf = new SparkConf().setMaster("local").setAppName("wordCount");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaPairRDD<String, String> csvData = sc.wholeTextFiles("");
        JavaRDD<String[]> javaRDD = csvData.flatMap(new ParseLine());
    }
}
