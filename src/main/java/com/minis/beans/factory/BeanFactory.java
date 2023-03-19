package com.minis.beans.factory;

import com.minis.BeanDefinition;
import com.minis.beans.BeansException;

public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;
    void registerBeanDefinition(BeanDefinition beanDefinition);
}
