package com.minis.context;

import com.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.minis.beans.factory.config.BeanFactoryPostProcessor;
import com.minis.beans.factory.config.ConfigurableListableBeanFactory;
import com.minis.beans.factory.support.DefaultListableBeanFactory;
import com.minis.beans.factory.xml.XmlBeanDefinitionReader;
import com.minis.core.ClassPathXmlResource;
import com.minis.core.Resource;

import java.util.ArrayList;
import java.util.List;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    DefaultListableBeanFactory beanFactory;
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    //context负责整个容器的启动过程，读外部配置，解析bean定义，创建BeanFactory
    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh){
        Resource resource = new ClassPathXmlResource(fileName);
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        if(isRefresh){
            try{
                refresh();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

//    public void refresh() throws BeansException,IllegalStateException{
//        //Register bean processors that intercept bean creation.
//        registerBeanPostProcessors((AutowiredCapableBeanFactory) this.beanFactory);
//        //Initialize other special beans in specific context subclasses.
//        onRefresh();
//    }

    void onRefresh() {
        this.beanFactory.refresh();
    }

//    private void registerBeanPostProcessors(AutowiredCapableBeanFactory beanFactory) {
//        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
//    }

    //public List<BeanFactoryPostProcessor>
    //context在对外提供一个getBean，底下就是调用BeanFactory对应的方法
//    public Object getBean(String beanName) throws BeansException{
//        return this.beanFactory.getBean(beanName);
//    }

    public boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName,obj);
    }

    public boolean isSingleton(String name) {
        return false;
    }

    public boolean isPrototype(String name) {
        return false;
    }

    public Class<?> getType(String name) {
        return null;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
    }

    @Override
    void finishRefresh() {
        publishEvent(new ContextRefreshEvent("context Refreshed"));
    }

    @Override
    void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    void initApplicationEventPublisher() {
        ApplicationEventPublisher aep = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(aep);
    }

    @Override
    void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    @Override
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        super.addBeanFactoryPostProcessor(postProcessor);
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }


}
