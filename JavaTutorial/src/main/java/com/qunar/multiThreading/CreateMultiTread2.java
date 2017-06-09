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

        String mainThreadName = Thread.currentThread().getName(); //得到当前运行的线程的名字
        System.out.println("主线程的名称为： "+mainThreadName);
        int i = 0;
        // 实现Runable接口的线程的运行方法
        Thread thread = new Thread(new CreateMultiTread2(),"实现runable接口的线程");
        thread.start();


        String threadName = thread.getName();

        System.out.println("创建的线程的名字为： "+threadName);




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
