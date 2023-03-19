package com.minis.beans.factory;

import com.minis.BeanDefinition;
import com.minis.beans.BeansException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleBeanFactory implements BeanFactory {
    private List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
    private List<String> beanNames = new ArrayList<String>();
    private Map<String, Object> singletons = new HashMap<String, Object>();

    public SimpleBeanFactory() {
    }

    //容器核心方法
    public Object getBean(String beanName) throws BeansException {
        //先尝试拿bean实例
        return null;
    }

    public void registerBeanDefinition(BeanDefinition beanDefinition) {

    }
}
