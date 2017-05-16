package com.qunar.sparkStreaming;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 说明:　从kafka接收数据,并统计输入数据中各单词出现的次数
 * key point:
 * 0. 启动zookeeper    bin/zookeeper-server-start.sh config/zookeeper.properties
 * 1. 启动kafka, 创建topic ("test")  bin/kafka-server-start.sh config/server.properties
 * 2. 创建topic bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
 * 3. 创建broker(产生消息源): bin/kafka-console-producer.sh --broker-list loacalhost:9092 --topic test
 * other point:
 * 1. 查看创建的topic: bin/kafka-topics.sh --list --zookeeper localhost:2181
 * 2. 查看broker产生的消息(创建consumer): bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning
 * Created by zhipengwu on 17-5-15.
 */
public class SparkStreamingKafkaTest {
    private static final Pattern SPACE = Pattern.compile(" ");


    public static void main(String[] args) throws InterruptedException {
        int numThreads=2;
        String zookeeper="localhost:2181";
        String group="qunar-group";
        Map<String,Integer> toppicMap=new HashMap<>();
        toppicMap.put("test",numThreads);
        SparkConf conf=new SparkConf().setMaster("local[2]").setAppName("networkwordcount");
        JavaStreamingContext jssc=new JavaStreamingContext(conf, Durations.seconds(5));
        JavaPairReceiverInputDStream<String, String> messages = KafkaUtils.createStream(jssc, zookeeper, group, toppicMap);
        JavaDStream<String> lines =  messages.map(new Function<Tuple2<String,String>,String>(){

            @Override
            public String call(Tuple2<String, String> stringStringTuple2) throws Exception {
                return stringStringTuple2._2();
            }
        });

        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(SPACE.split(s)).iterator();
            }
        });

        JavaPairDStream<String, Integer> wordCounts = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s,1);
            }
        }).reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer+integer2;
            }
        });

        wordCounts.print();
        jssc.start();
        jssc.awaitTermination();




    }

}
