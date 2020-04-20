package com.webserver.http;

import com.webserver.exception.EmptyRequestException;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
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

    /**
     * url中去除拼接参数的部分
     */
    private String requestUri;

    /**
     * url中拼接的参数
     */
    private String queryString;

    /**
     * url中参数列表 key：参数名 value:参数值
     */
    private  Map<String, String> parameters = new HashMap<>();

    /**
     * http协议中消息头的集合
     */
    private Map<String , String> headers = new HashMap<>();

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
            parseHeaders();
            parseContent();
        } catch (EmptyRequestException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析HTTP消息正文
     * 看Headers中是否含有Content-Length来判定是否含有消息正文
     * Content-Type是告知消息正文是什么类型的
     */
    private void parseContent() {
        logger.info("解析消息正文信息");
        try {
            if(headers.containsKey("Content-Length")) {
                int length = Integer.parseInt(headers.get("Content-Length"));
                byte[] data = new byte[length];
                inputStream.read(data);
                String type = headers.get("Content-Type");
                if("application/x-www-form-urlencoded".equals(type)){
                    String line = new String(data,"ISO8859-1");
                    line = URLDecoder.decode(line , "UTF-8");
                    parseParameters(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析HTTP消息头
     */
    private void parseHeaders() {
        logger.info("开始解析消息头");
        try{
            String line = null;
            while (!"".equals(line = readLine())) {
                String[] data = line.split(":");
                headers.put(data[0] , data[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("解析消息头完毕");
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
        logger.info("解析请求行完毕");
    }

    /**
     * 解析url 如果是get方法url中可能有拼接参数
     */
    private void parseURL() {
        logger.info("解析url");
        if(url.indexOf("?") == -1){
            requestUri = url;
        } else {
            String[] data = url.split("\\?");
            requestUri = data[0];
            if(data.length > 1){
                queryString = data[1];
                try {
                    queryString = URLDecoder.decode(queryString,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                parseParameters(queryString);
            }
            logger.info("解析url完毕");
        }
    }

    /**
     * 解析参数：name=value&name=value&...
     * @param queryString
     */
    private void parseParameters(String queryString) {
        String[] data = queryString.split("&");
        for (String para : data) {
            String[] parameter = para.split("=");
            if (parameter.length > 1){
                parameters.put(parameter[0] , parameter[1]);
            } else {
                parameters.put(parameter[0] , null);
            }
        }
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
