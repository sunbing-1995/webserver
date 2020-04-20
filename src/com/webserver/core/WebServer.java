package com.webserver.core;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
    /**
     * log4j打印日志使用
     */
    static Logger logger = Logger.getLogger(WebServer.class);

    /**
     * 传输层tcp 套接字封装tcp
     */
    private ServerSocket serverSocket;

    /**
     * 线程池接口
     */
    private ExecutorService executorService;

    private WebServer() {
        try {
            logger.info("正在启动服务端..");
            //监听8089端口
            serverSocket = new ServerSocket(8089);
            //创建固定线程的线程池
            /*
             * ThreadPoolExecutor(int corePoolSize,
             *                    int maximumPoolSize,
             *                    long keepAliveTime,
             *                    TimeUnit unit,
             *                    BlockingQueue<Runnable> workQueue,
             *                    ThreadFactory threadFactory
             *                    RejectedExecutionHandler handler);
             *  corePoolSize 核心线程数，一旦创建不在释放
             *  maximumPoolSize 最大线程数
             *  keepAliveTime 非核心线程的存活时间
             *  unit 时间单位
             *  workQueue<Runnable> 任务队列
             *      1.ArrayBlockingQueue 基于数组结构的有界阻塞队列,必须设置容量;
             *      2.LinkedBlockingQueue 基于链表结构的阻塞队列，可以设置容量；吞吐量通常要高于ArrayBlockingQueue
             *      3.SynchronousQueue 不存储元素的阻塞队列;每个插入offer操作必须等到另一个线程调用移除poll操作，
             *      否则插入操作一直处于阻塞状态，吞吐量通常要高于LinkedBlockingQueue
             *      4.PriorityBlockingQueue 具有优先级的无限阻塞队列
             *  threadFactory 线程工厂
             *  handler 当队列排满且到达最大线程数时，用于处理阻塞的线程
             *
             *  1.CachedThreadPool 可缓存线程池
             *      ThreadPoolExecutor(0, Integer.MAX_VALUE,
             *                          60L, TimeUnit.SECONDS,
             *                          new SynchronousQueue<Runnable>());
             *  2.SingleThreadPool 单线程池
             *      ThreadPoolExecutor(1, 1,
             *                         0L, TimeUnit.MILLISECONDS,
             *                         new LinkedBlockingQueue<Runnable>())
             *  3.FixedThreadPool 固定线程池
             *      ThreadPoolExecutor(nThreads, nThreads,
             *                          0L, TimeUnit.MILLISECONDS,
             *                          new LinkedBlockingQueue<Runnable>());
             *  4.ScheduledThreadPool 固定线程数，支持定时和周期性任务线程池
             *      ThreadPoolExecutor(corePoolSize, Integer.MAX_VALUE,
             *                          DEFAULT_KEEPALIVE_MILLIS, MILLISECONDS,
             *                          new DelayedWorkQueue())
             *
             *
             */
            executorService = Executors.newFixedThreadPool(100);
            logger.info("服务端启动完毕");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start(){
        try {
            logger.info("等待客户端连接..");
            Socket socket = serverSocket.accept();
            logger.info("客户端已连接");
            ClientHander hander = new ClientHander(socket);
            executorService.execute(hander);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebServer webServer = new WebServer();
        webServer.start();
    }
}
