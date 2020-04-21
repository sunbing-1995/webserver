package com.webserver.http;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpRespone {
    /**
     * 打印日志使用
     */
    static Logger logger = Logger.getLogger(HttpRespone.class);

    /**
     * 传入的套接字 用来获取流
     */
    private Socket socket;

    /**
     * 输出流
     */
    private OutputStream outputStream;

    public HttpRespone(Socket socket){
        try {
            this.socket = socket;
            this.outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
