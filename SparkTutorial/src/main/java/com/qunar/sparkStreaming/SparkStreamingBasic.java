package com.qunar.sparkStreaming;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

/**
 * Created by zhipengwu on 17-5-10.
 */
public class SparkStreamingBasic {
    public static void main(String[] args) {
        String sparkMaster="local[2]";
        String appName="spark_streaming_basic";
        String fileSystemInputDirectory="";

        //step1. 创建SparkConf对象
        SparkConf sparkConf=new SparkConf().setMaster(sparkMaster).setAppName(appName);

        //step2. 创建JavaStreamingContext对象
        JavaStreamingContext javaStreamingContext=new JavaStreamingContext(sparkConf,new Duration(5000));

        //step 3. 从数据源接收数据创建DStream对象
//        JavaPairInputDStream<Object, Object> objectObjectJavaPairInputDStream = javaStreamingContext.fileStream(fileSystemInputDirectory, String.class, String.class, StreamInputFormat.class);
    }
}
