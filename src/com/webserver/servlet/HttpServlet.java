package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpRespone;

public abstract class HttpServlet {
    /**
     * 用于处理请求的方法
     * @param httpRequest
     * @param httpRespone
     */
    public abstract void service(HttpRequest httpRequest , HttpRespone httpRespone);
}
