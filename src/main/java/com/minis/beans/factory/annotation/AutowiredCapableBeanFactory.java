package com.minis.beans.factory.annotation;

import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;

public interface AutowiredCapableBeanFactory extends BeanFactory {
    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;
    Object applyBeanPostProcessorsBeforeInitialization(Object bean, String beanName) throws BeansException;
    Object applyBeanPostProcessorsAfterInitialization(Object bean, String beanName) throws BeansException;
}
