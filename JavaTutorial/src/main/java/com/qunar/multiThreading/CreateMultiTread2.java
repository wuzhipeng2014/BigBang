package com.qunar.multiThreading;

/**
 * Created by zhipengwu on 17-5-27. 实现Runnable接口实现多线程创建 ps: 实现了Runable接口的方式创建的多线程可以方便的被线程池管理 如果线程池满了， 新的线程就会排队等候
 */
public class CreateMultiTread2 implements Runnable {

    @Override
    public void run() {
        int i = 0;
        while (i++ < 10) {
            System.out.println("子线程:    " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int i = 0;
        CreateMultiTread2 createMultiThread = new CreateMultiTread2();
        // 实现Runable接口的线程的运行方法
        Thread thread = new Thread(new CreateMultiTread2());
        thread.start();
        while (i++ < 10) {
            System.out.println("主线程:    " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 创建实现了Runable接口的匿名类实现多线程创建
        Runnable myRunable = new Runnable() {
            @Override
            public void run() {
                int j = 0;
                while (j++ < 10) {
                    System.out.println("匿名Runable接口类： " + j);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread thread1 = new Thread(myRunable);

        thread1.start();

    }
}
