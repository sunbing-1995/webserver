package com.webserver.core;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpRespone;
import com.webserver.servlet.HttpServlet;

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
            HttpRespone httpRespone = new HttpRespone(socket);

            //处理请求
            /**
             * 通过请求获取请求路径
             */
            String path = httpRequest.getRequestUri();
            /**
             * 通过路径获取对应的servlet
             */
            HttpServlet httpServlet = ServerContext.getServlet(path);

            if (httpServlet != null) {
                httpServlet.service(httpRequest , httpRespone);
            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
