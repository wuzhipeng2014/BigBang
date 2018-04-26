package com.qunar.interviewtest;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by zhipengwu on 17-2-20.
 */
public class HelloSpark {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setMaster("local").setAppName("wordCount");
        JavaSparkContext sc = new JavaSparkContext(conf);
        //从本地文件系统读取数据
        JavaRDD<String> stringJavaRDD = sc.textFile("/home/zhipengwu/secureCRT/feature_weight_test.txt");

        JavaRDD<String> derbys = stringJavaRDD.filter(new Function<String, Boolean>() {
            public Boolean call(String s) throws Exception {
                return s.contains("derby");
            }
        });
        JavaRDD<String> javas = stringJavaRDD.filter(new Function<String, Boolean>() {
            public Boolean call(String s) throws Exception {
                return s.contains("java");
            }
        });

        JavaRDD<String> unions = derbys.union(javas);
        long count = unions.count();
        System.out.println("*********************************");
        System.out.println("unions.count="+count);
        for (String line:unions.collect()){
            System.out.println(line);
        }



        // 将输入的文件切分为单词
        JavaRDD<String> words = stringJavaRDD.flatMap(new FlatMapFunction<String, String>() {
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(" ")).iterator();
            }
        });

        // 将切分的单词结果转换为pair类型
        JavaPairRDD<String, Integer> counts = words.mapToPair(new PairFunction<String, String, Integer>() {
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<String, Integer>(s, 1);
            }
        }).reduceByKey(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        });

        counts.saveAsTextFile("/home/zhipengwu/secureCRT/spark/counts.txt");

        sc.stop();
    }
}
