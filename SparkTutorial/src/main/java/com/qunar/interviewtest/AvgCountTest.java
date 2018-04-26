package com.qunar.interviewtest;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by zhipengwu on 17-2-23.
 */
public class AvgCountTest {

   static Function<Integer,AvgCount> createAcc=new Function<Integer,AvgCount>(){

        public AvgCount call(Integer integer) throws Exception {
            return new AvgCount(integer,1);
        }
    };

   static Function2<AvgCount,Integer,AvgCount> addandCount=new Function2<AvgCount, Integer, AvgCount>() {
        public AvgCount call(AvgCount avgCount, Integer integer) throws Exception {
            avgCount.total_+=integer;
            avgCount.num_+=1;
            return avgCount;
        }
    };

   static Function2<AvgCount,AvgCount,AvgCount> combine=new Function2<AvgCount, AvgCount, AvgCount>() {
        public AvgCount call(AvgCount avgCount, AvgCount avgCount2) throws Exception {
            avgCount.num_+=avgCount2.num_;
            avgCount.total_+=avgCount2.total_;
            return avgCount;
        }
    };
    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setMaster("local").setAppName("AvgCountTest");
        JavaSparkContext sc = new JavaSparkContext(conf);
        final AvgCount initial=new AvgCount(0,0);
        JavaRDD<Integer> parallelize = sc.parallelize(Arrays.asList(1, 2, 3, 4,1,2,4,3,5));
        JavaPairRDD javaPairRDD = parallelize.mapToPair(new PairFunction<Integer, Object, Object>() {

            public Tuple2<Object, Object> call(Integer integer) throws Exception {
                return new Tuple2<Object, Object>(integer, integer);
            }
        });

        List collect = javaPairRDD.collect();


        JavaPairRDD avgCounts = javaPairRDD.combineByKey(createAcc, addandCount, combine);
        Map<Integer,AvgCount> countMap = avgCounts.collectAsMap();
        for (Map.Entry<Integer,AvgCount> entry:countMap.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue().avg());
        }


    }
}
