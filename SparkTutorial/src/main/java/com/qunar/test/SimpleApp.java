package com.qunar.test;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

/**
 * Created by zhipengwu on 17-4-28.
 */
public class SimpleApp {
    public static void main(String[] args) {
//        String logfile="/home/zhipengwu/secureCRT/homepage_local_entrance_single_entry_uv_sta_new_pooled_20170428.txt";
        String logfile="data/input/homepage_local_entrance_single_entry_uv_sta_new_pooled_20170428.txt";
//        SparkConf conf=new SparkConf().setMaster("local").setAppName("simple Application");
        SparkConf conf=new SparkConf().setAppName("simple Application");
        JavaSparkContext sc=new JavaSparkContext(conf);
        JavaRDD<String> logData = sc.textFile(logfile).cache();
        long countAs = logData.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String s) throws Exception {
                return s.contains("A");
            }
        }).count();

        long countBs = logData.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String s) throws Exception {
                return s.contains("B");
            }
        }).count();
        System.out.println(String.format("countAs=%s; countBs=%s",countAs,countBs));
        sc.stop();

    }
}
