package com.minis.beans.factory.support;

import com.minis.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    //容器中存放所有bean的名称的列表
    protected List<String> beanNames = new ArrayList<String>();
    //容器中存放所有bean实例的map
    protected Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(256);
    protected final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<String, Object>(16);

    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects) {
            Object oldObject = this.singletonObjects.get(beanName);
            if (oldObject != null) {
                throw new IllegalStateException("Could not register object [" + singletonObject +
                        "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
            }
            this.singletonObjects.put(beanName, singletonObject);
            this.beanNames.add(beanName);
            System.out.println(" bean registerded............. " + beanName);
        }
    }

    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }

    public boolean containsSingleton(String beanName) {
        return this.singletonObjects.containsKey(beanName);
    }

    public String[] getSingletonNames() {
        return (String[]) this.beanNames.toArray();
    }

    protected void removeSingleton(String beanName){
        synchronized (this.singletonObjects){
            this.singletonObjects.remove(beanName);
            this.beanNames.remove(beanName);
        }
    }
}
