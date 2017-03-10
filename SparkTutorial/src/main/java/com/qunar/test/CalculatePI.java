package com.qunar.test;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhipengwu on 17-3-10.
 */
public class CalculatePI {
    public static int NUM_SAMPLES=10000000;

    public static void main(String[] args) {
        SparkConf conf=new SparkConf().setMaster("local").setAppName("CalculatePI");
        JavaSparkContext sparkContext=new JavaSparkContext(conf);

        List<Integer> l=new ArrayList<>(NUM_SAMPLES);
        for (int i=0;i<NUM_SAMPLES;i++){
            l.add(i);
        }
        long count = sparkContext.parallelize(l).filter(i -> {
            double x = Math.random();
            double y = Math.random();
            return x*x + y*y < 1;
        }).count();
        double v = 4.0 * count / NUM_SAMPLES;
        System.out.println(String.format("PI=%s",4.0*count/NUM_SAMPLES));

    }
}
