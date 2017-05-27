package com.qunar.multiThreading;

/**
 * Created by zhipengwu on 17-5-27.
 * 实现Runnable接口实现多线程创建
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
        //实现Runable接口的线程的运行方法
        Thread thread=new Thread(new CreateMultiTread2());
        thread.start();
        while (i++ < 10) {
            System.out.println("主线程:    " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
