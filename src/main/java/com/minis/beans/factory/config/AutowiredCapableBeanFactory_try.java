package com.minis.beans.factory.config;

import com.minis.beans.BeansException;
import com.minis.beans.factory.support.AbstractBeanFactory;
import com.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;

import java.util.ArrayList;
import java.util.List;

public class AutowiredCapableBeanFactory_try extends AbstractBeanFactory {
    private final List<AutowiredAnnotationBeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public void addBeanPostProcessor(AutowiredAnnotationBeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public int getBeanPostProcessorCount(){
        return this.beanPostProcessors.size();
    }

    public List<AutowiredAnnotationBeanPostProcessor> getBeanPostProcessors(){
        return this.beanPostProcessors;
    }

    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object result = bean;
        for (AutowiredAnnotationBeanPostProcessor beanPostProcessor: getBeanPostProcessors()) {
            beanPostProcessor.setBeanFactory(this);
            result = beanPostProcessor.postProcessBeforeInitialization(result,beanName);
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object bean, String beanName) throws BeansException {
        Object result = bean;
        for (BeanPostProcessor beanProcessor :
                getBeanPostProcessors()) {
            result = beanProcessor.postProcessAfterInitialization(result,beanName);
        }
        return result;
    }

    //byType
    public Object getBean(String name,Class<?> classType) throws BeansException {
        String beanName = classType.getSimpleName();
        return super.getBean(beanName.substring(0,1).toLowerCase()+beanName.substring(1));
    }
}
