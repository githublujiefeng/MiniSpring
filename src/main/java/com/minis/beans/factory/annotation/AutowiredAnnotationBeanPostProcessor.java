package com.minis.beans.factory.annotation;

import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.config.BeanPostProcessor;
import com.minis.beans.factory.config.ConfigurableListableBeanFactory;
import com.minis.beans.factory.support.DefaultListableBeanFactory;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object result = bean;
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {
            //对每一个属性进行判断，如果带有@Autowired注解则进行处理
            for (Field field :
                    fields) {
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if(isAutowired){
                    //根据属性名找同名的bean
                    String filedName = field.getName();
                    Class<?> type = field.getType();
                    //Autowired ByType查找bean
                    Object obj = ((DefaultListableBeanFactory)this.getBeanFactory()).getBean(filedName,type);
                //    Object obj = this.getBeanFactory().getBean(filedName);
                    try {
                        //设置属性值，完成注入
                        field.setAccessible(true);
                        field.set(bean,obj);
                        System.out.println("autowire "+type.getSimpleName() +" "
                                + filedName+" for bean "+beanName);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return result;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    public ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
