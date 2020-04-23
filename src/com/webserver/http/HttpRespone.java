package com.webserver.http;

import lombok.Data;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

@Data
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

    /**
     * 响应正文的文件
     */
    private File entity;

    public HttpRespone(Socket socket){
        try {
            this.socket = socket;
            this.outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 响应客户端以一个HTTP格式
     */
    public void flush(){
        sendStatusLine();
        sendHeader();
        sendContent();
    }

    /**
     * 发送响应正文
     */
    private void sendContent() {
    }

    /**
     * 发送响应头
     */
    private void sendHeader() {
    }

    /**
     * 发送状态行
     */
    private void sendStatusLine() {
    }

    public void setEntity(File file) {
        this.entity = file;
        String fileName = entity.getName();
        String ext = fileName.substring(fileName.lastIndexOf(".")+1);


    }
}
