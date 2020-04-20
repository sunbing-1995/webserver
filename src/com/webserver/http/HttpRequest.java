package com.webserver.http;

import com.webserver.exception.EmptyRequestException;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Logger;

@Data
public class HttpRequest {
    /**
     * 打印日志使用
     */
    static Logger logger = Logger.getLogger(String.valueOf(HttpRequest.class));

    /**
     * 传入的套接字 用来获取流
     */
    private Socket socket;

    /**
     * 接收输入流
     */
    private InputStream inputStream;

    /**
     * 请求方式
     */
    private String method;

    /**
     * url
     */
    private String url;

    /**
     * 协议
     */
    private String protocol;

    public HttpRequest(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        /**
         * 解析请求分为三部分
         * 1.解析请求行
         * 2.解析消息头
         * 3.解析消息正文
         */
        try {
            parseRequestLine();
        } catch (EmptyRequestException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析请求行
     */
    private void parseRequestLine() throws EmptyRequestException {
        logger.info("开始解析请求行");
        try {
            String line = readLine();
            if ("".equals(line)){
                throw new EmptyRequestException();
            }
            logger.info("请求行是："+line);

            /**
             * 请求行拆分为method URI protocol
             * 空格拆分
             */
            String[] data = line.split("\\s");
            method = data[0];
            url = data[1];
            protocol = data[2];
            parseURL();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseURL() {
        logger.info("解析url");

    }

    /**
     * 按行读取字符串
     * 每行结尾为cr换行 lf回车
     * @return
     */
    private String readLine() throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        //c1表示上次读取到的字符，c2表示本次读取到的字符
        int c1 = -1,c2 = -1;
        while (-1 != (c2 = inputStream.read())){
            if (c1 == 13&&c2==10){
                break;
            }
            stringBuffer.append((char)c2);
            c1 = c2;
        }
        return stringBuffer.toString().trim();
    }
}
