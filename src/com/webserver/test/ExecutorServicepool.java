package com.webserver.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServicepool {
    public static void main(String[] args) throws InterruptedException {
        int[] a = new int[1];
        ExecutorService executorsServer = Executors.newFixedThreadPool(5);
        for (int i = 0; i<15;i++){
            executorsServer.execute(new TestRunnable(a));
            System.out.println("================ "+i);
            Thread.sleep(1000);
            System.out.println("主线程休眠了1秒钟");
        }
    }
}
