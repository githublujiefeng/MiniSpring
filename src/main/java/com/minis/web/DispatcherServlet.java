package com.minis.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String sContextConfigLocation;
    private List<String> packageNames = new ArrayList<>();
    private Map<String,Object> controllerObjs = new HashMap<>();
    private List<String> controllerNames = new ArrayList<>();
    private Map<String,Class<?>> controllerClasses = new HashMap<>();
    private List<String> urlMappingNames = new ArrayList<>();

    private Map<String,MappingValue> mappingValues;
    private Map<String,Class<?>> mappingClz = new HashMap<>();
    private Map<String,Object> mappingObjs = new HashMap<>();


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sContextConfigLocation = config.getInitParameter("contextConfigLocation");
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Resource rs = new ClassPathXmlResource(xmlPath);
        XmlConfigReader reader = new XmlConfigReader();
        mappingValues = reader.loadConfig(rs);
        Refresh();
    }

    protected void Refresh(){
        for (Map.Entry<String, MappingValue> entry :
                mappingValues.entrySet()) {
            String id = entry.getKey();
            String className = entry.getValue().getClz();
            Object obj = null;
            Class<?> clz = null;
            try {
                clz = Class.forName(className);
                obj = clz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mappingClz.put(id,clz);
            mappingObjs.put(id,obj);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sPath = req.getServletPath();
        if(this.mappingValues.get(sPath)==null){
            return;
        }
        Class<?> clz = this.mappingClz.get(sPath);
        Object obj = this.mappingObjs.get(sPath);
        String methodName = this.mappingValues.get(sPath).getMethod();
        Object objResult = null;
        try{
            Method method = clz.getMethod(methodName);
            objResult = method.invoke(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
        resp.getWriter().append(objResult.toString());
    }
}
