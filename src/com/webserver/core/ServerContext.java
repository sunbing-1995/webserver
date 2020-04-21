package com.webserver.core;

import com.webserver.servlet.HttpServlet;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerContext {
    /**
     * 请求路径与对应Servlet的关系
     */
    private static Map<String , HttpServlet> servletMapping = new HashMap<>();

    static {
        initServletMapping();
    }

    private static void initServletMapping() {
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read("./resource/servlet.xml");
            Element rootElement = document.getRootElement();
            List<Element> sevlets = rootElement.elements();
            for (Element e : sevlets) {
                String path = e.attributeValue("path");
                String className = e.attributeValue("className");
                Class cls = Class.forName(className);
                HttpServlet servlet = (HttpServlet) cls.newInstance();
                servletMapping.put(path , servlet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HttpServlet getServlet(String path) {
        return servletMapping.get(path);
    }
}
