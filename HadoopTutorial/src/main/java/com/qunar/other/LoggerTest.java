package com.qunar.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhipengwu on 17-1-6.
 */
public class LoggerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerTest.class);


    public static void main(String[] args) {
        int a=10;
        int b=5;
        int c=0;
        try {

        if (a/b>0){
            LOGGER.info("a/b");
        }
        if (a/c>0){
            LOGGER.info("a/c");
        }
        }catch (Exception e){
            LOGGER.error("执行过程中发生错误,{}", e);
        }

    }
}
