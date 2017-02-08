package com.qunar.guavaExercise;

import static com.google.common.base.Preconditions.checkArgument;

import org.junit.Test;

/**
 * Created by zhipengwu on 16-12-16.
 */
public class PreconditionExercise {

    public static void main(String[] args) {


    }

    @Test
    public void checkArgumentTest(){
        int a=2;
        int b=5;
        // 参数检查异常时,立即终止程序执行
        checkArgument(a>b,"a must larger than b, %s %s",a,b);
        System.out.println("exec next");

    }
}
