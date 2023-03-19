package com.minis.beans.factory;

import com.minis.beans.BeanDefinition;
import com.minis.beans.BeansException;
import com.minis.beans.factory.support.DefaultSingletonBeanRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleSingletonBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory{
    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<String, BeanDefinition>();

    public SimpleSingletonBeanFactory() {
    }


    //容器核心方法
    public Object getBean(String beanName) throws BeansException {
        //先尝试拿bean实例
        Object singleton = singletons.get(beanName);
        //如果此时还没有这个Bean实例，则获取它的定义来创建实例
        if(singleton==null){
            int index = beanNames.indexOf(beanName);
            if(index==-1){
                throw new BeansException("bean未找到！");
            }else{
                //获取bean的定义并生成
                BeanDefinition beanDefinition = beanDefinitions.get(index);
                try {
                    singleton = Class.forName(beanDefinition.getClassName()).newInstance();
                    singletons.put(beanDefinition.getId(),singleton);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return singleton;
    }
    public void registerBeanDefinition(BeanDefinition beanDefinition){
        this.beanDefinitions.put(beanDefinition.getId(),beanDefinition);
    }

    public Boolean containsBean(String name) {
        return containsSingleton(name);
    }

    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

}
