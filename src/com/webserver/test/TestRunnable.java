package com.webserver.test;

public class TestRunnable implements Runnable {
    public int[] count;

    public TestRunnable(int[] count) {
        this.count = count;
    }

    @Override
    public void run() {
        try {
            if(Thread.currentThread().getName().equals("pool-1-thread-1")){
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"-线程被调用了");
        System.out.println("count值为："+(++count[0]));
    }
}
