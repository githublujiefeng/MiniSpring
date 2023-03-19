package com.minis.beans.factory.xml;

import com.minis.beans.BeanDefinition;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.SimpleBeanFactory;
import com.minis.beans.factory.SimpleSingletonBeanFactory;
import com.minis.core.Resource;
import org.dom4j.Element;

public class XmlBeanDefinitionReader {
    SimpleSingletonBeanFactory simpleSingletonBeanFactory;

    public XmlBeanDefinitionReader(SimpleSingletonBeanFactory simpleSingletonBeanFactory){
        this.simpleSingletonBeanFactory = simpleSingletonBeanFactory;
    }

    public void loadBeanDefinitions(Resource resource){
        while(resource.hasNext()){
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
            this.simpleSingletonBeanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
