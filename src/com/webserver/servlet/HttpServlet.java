package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpRespone;

import java.io.File;

public abstract class HttpServlet {
    /**
     * 用于处理请求的方法。
     * ClientHandler在调用某请求对应的处理类(某Servlet)时，会调用其service方法
     * @param httpRequest
     * @param httpRespone
     */
    public abstract void service(HttpRequest httpRequest , HttpRespone httpRespone);

    public void forward(String path , HttpRequest httpRequest , HttpRespone httpRespone) {
        File file = new File("./webapp"+path);
        httpRespone.setEntity(file);
    }
}
