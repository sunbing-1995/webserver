package com.webserver.test;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TestXMLReader {
    public static void main(String[] args) throws IOException {
        try {
            /**
             * dom4j解析xml
             */
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read("./resource/servlet.xml");
            Element rootElement = document.getRootElement();

            /**
             * jsoup解析xml
             */
            File file = new File("./resource/servlet.xml");
            org.jsoup.nodes.Document document1 = Jsoup.parse(file , "UTF-8");
            System.out.println(document1);
            Elements elements = document1.getElementsByTag("servlet");
            for (org.jsoup.nodes.Element elem : elements) {
                System.out.println(elem);
            }

            URL url = new URL("http://www.baidu.com");
            org.jsoup.nodes.Document document2 = Jsoup.parse(url , 5000);
            System.out.println(document2);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
