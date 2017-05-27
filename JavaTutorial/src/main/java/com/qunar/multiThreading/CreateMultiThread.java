package com.qunar.multiThreading;

import org.junit.Test;

/**
 * java多线程练习 Created by zhipengwu on 17-5-27.
 * 继承自Tread类实现多线程创建
 */
public class CreateMultiThread extends Thread {
    @Override
    public void run() {
        int i = 0;
        while (i++ < 10) {
            System.out.println("子线程:    " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void runCreateThread() {
        int i = 0;
        //直接创建此类的实例，并调用其start方法实现多线程运行
        CreateMultiThread createMultiThread = new CreateMultiThread();
        createMultiThread.start();
        while (i++ < 10) {
            System.out.println("主线程:    " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
