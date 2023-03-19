package com.minis.context;

import com.minis.beans.BeanDefinition;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassPathXmlApplicationContext_bak {
    private List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
    private Map<String, Object> singletons = new HashMap<String, Object>();
    //构造器获取外部配置，解析出bean的定义，形成内存映像
    public ClassPathXmlApplicationContext_bak(String fileName){
        this.readXml(fileName);
        this.instanceBeans();
    }

    //利用反射创建bean实例，并存储在singletons
    private void instanceBeans() {
        for (BeanDefinition beanDefinition:
             beanDefinitions) {
            try {
                singletons.put(beanDefinition.getId(),
                Class.forName(beanDefinition.getClassName()).newInstance());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void readXml(String fileName) {
        SAXReader saxReader = new SAXReader();
        try {
            URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();
            //对配置文件中的每一个bean标签进行处理
            for (Element element :
                    rootElement.elements()) {
                //获取bean的基本信息
                String BeanId = element.attributeValue("id");
                String BeanClassName = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(BeanId, BeanClassName);
                beanDefinitions.add(beanDefinition);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    //对外提供一个方法，让外部程序从容器中获取bean实力，并逐步演化成核心方法
    public Object getBean(String beanName){
        return singletons.get(beanName);
    }
}
