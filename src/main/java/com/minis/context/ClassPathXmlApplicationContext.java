package com.minis.context;

import com.minis.beans.BeanDefinition;
import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.SimpleSingletonBeanFactory;
import com.minis.core.Resource;
import com.minis.beans.factory.SimpleBeanFactory;
import com.minis.core.ClassPathXmlResource;
import com.minis.beans.factory.xml.XmlBeanDefinitionReader;

public class ClassPathXmlApplicationContext implements BeanFactory{
    BeanFactory beanFactory;

    //context负责整个容器的启动过程，读外部配置，解析bean定义，创建BeanFactory
    public ClassPathXmlApplicationContext(String fileName){
        Resource resource = new ClassPathXmlResource(fileName);
        SimpleSingletonBeanFactory beanFactory = new SimpleSingletonBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }

    //context在对外提供一个getBean，底下就是调用BeanFactory对应的方法
    public Object getBean(String beanName) throws BeansException{
        return this.beanFactory.getBean(beanName);
    }

    public Boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName,obj);
    }


}
