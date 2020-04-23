package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpRespone;
import org.apache.log4j.Logger;

import java.io.RandomAccessFile;
import java.util.Arrays;

public class RegServlet extends HttpServlet {
    /**
     * 用于打印日志
     */
    static Logger logger = Logger.getLogger(RegServlet.class);

    @Override
    public void service(HttpRequest httpRequest, HttpRespone httpRespone) {
        logger.info("RegServlet:开始注册信息");
        String username = httpRequest.getParameter("username");
        String password = httpRequest.getParameter("password");
        String nickname = httpRequest.getParameter("nickname");
        int age = Integer.parseInt(httpRequest.getParameter("age"));
        logger.info("username:"+username+"\\npassword:"+password+"\\nnickname:"+nickname+"\\nage:"+age);
        /**
         * 将信息写入本地文件
         */
        try (RandomAccessFile randomAccessFile = new RandomAccessFile("user.dat","rw")) {
            for (int i = 0 ; i<randomAccessFile.length();i++) {
                randomAccessFile.seek(i*100);
                byte[] data = new byte[32];
                randomAccessFile.read(data);
                String name = new String(data , "UTF-8").trim();
                if (name.equals(username)) {
                    forward("/myweb/reg_have_user.html" , httpRequest , httpRespone);
                    return;
                }
            }
            randomAccessFile.seek(randomAccessFile.length());
            writeData(username , randomAccessFile);
            writeData(password , randomAccessFile);
            writeData(nickname , randomAccessFile);
            randomAccessFile.write(age);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("注册完毕");
    }

    public void writeData (String parameter , RandomAccessFile randomAccessFile) throws Exception {
        byte[] data = parameter.getBytes("UTF-8");
        data = Arrays.copyOf(data , 32);
        randomAccessFile.write(data);
    }
}
