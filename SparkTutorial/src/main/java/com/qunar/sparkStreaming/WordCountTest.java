package com.qunar.sparkStreaming;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * spark streaming入门程序, 统计接收单词的数量
 * 说明:　运行函数之前,需要先执行shell命令:　nc -lk 9999 　以开始监听9999端口
 * Created by zhipengwu on 17-5-9.
 */
public class WordCountTest  {
    public static void main(String[] args) {

        //local[n] 其中n指定了程序运行启动的线程的个数,此处n的值要大于接收线程的个数,多余的线程可用于对接收到的数据进行处理
        SparkConf sparkConf=new SparkConf().setMaster("local[2]").setAppName("SparkWordCountTest");
        JavaStreamingContext javaStreamingContext = new JavaStreamingContext(sparkConf, Durations.seconds(5));
        //从netcat 服务器接收数据创建DStream
        JavaReceiverInputDStream<String> lines = javaStreamingContext.socketTextStream("localhost", 9999);
        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(" ")).iterator();
            }
        });
        JavaPairDStream<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<String, Integer>(s, 1);
            }
        });
        JavaPairDStream<String, Integer> wordCounts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        });
        System.out.println("####################################");
        wordCounts.print();

        System.out.println("end##################################");

        javaStreamingContext.start();
        try {
            javaStreamingContext.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
