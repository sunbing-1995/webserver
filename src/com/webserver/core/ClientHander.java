package com.webserver.core;

import com.webserver.http.HttpRequest;

import java.io.IOException;
import java.net.Socket;

public class ClientHander implements Runnable {
    private Socket socket;

    public ClientHander(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            /**
             * 处理请求
             * 浏览器传过来的是按应用层的http协议，所以按HTTP格式处理
             */
            HttpRequest httpRequest = new HttpRequest(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
