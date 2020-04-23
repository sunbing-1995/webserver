package com.webserver.core;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpRespone;
import com.webserver.servlet.HttpServlet;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class ClientHander implements Runnable {
    static Logger logger = Logger.getLogger(ClientHander.class);

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
                File file = new File("./webapps"+path);
                if (file.exists()) {
                    logger.info("没有对应的Servlet,已找到对应资源"+path);
                    httpRespone.setEntity(file);
                } else {
                    logger.info("该资源不存在");
                }
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
