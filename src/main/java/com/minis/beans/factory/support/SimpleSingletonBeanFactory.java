package com.minis.beans.factory.support;

import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.config.BeanDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleSingletonBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(256);
    private List<String> beanDefinitionNames = new ArrayList<String>();
    public SimpleSingletonBeanFactory() {
    }


/*    //容器核心方法
    public Object getBean(String beanName) throws BeansException {
        //先尝试拿bean实例
        Object singleton = singletonObjects.get(beanName);
        //如果此时还没有这个Bean实例，则获取它的定义来创建实例
        if(singleton==null){
            //如果没有实例，尝试从毛胚实例中获取
            singleton = this.earlySingletonObjects.get(beanName);
            if(singleton==null){
                //如果没有毛坯，则创建bean实例并注册
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                singleton = createBean(beanDefinition);
                this.registerBean(beanName,singleton);
                //预留beanpostprocessor
                //step 1：postProcessBeforeInitialization
                //step 2: afterPropertiesSet
                //step 3: init-method
                //step 4: postProcessAfterInitialization
            }

        }
        if (singleton == null) {
            throw new BeansException("bean is null.");
        }
        return singleton;
    }
    public void registerBeanDefinition(BeanDefinition beanDefinition){
        this.beanDefinitionMap.put(beanDefinition.getId(),beanDefinition);
    }

    public boolean containsBean(String name) {
        return containsSingleton(name);
    }

    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isLazyInit();
    }

    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    public Class<?> getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name,beanDefinition);
        this.beanDefinitionNames.add(name);
        System.out.println("registerBeanDefinition: "+ name);
        if(!beanDefinition.isLazyInit()){
            try{
                getBean(name);
            }catch (BeansException e){
                e.printStackTrace();
            }
        }
    }

    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    private Object createBean(BeanDefinition beanDefinition){
        Class<?> clz = null;
        //创建毛胚bean实例
        Object obj = doCreateBean(beanDefinition);
        //存放到毛胚实例缓存中
        this.earlySingletonObjects.put(beanDefinition.getId(),obj);
        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        handleProperties(beanDefinition,clz,obj);
        return obj;
    }

    //doCreateBean创建毛坯实例，仅仅调用构造方法，没有进行属性处理
    private Object doCreateBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Object obj = null;
        try {
            clz = Class.forName(beanDefinition.getClassName());
            //处理构造器参数
            ConstructorArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
            if (!argumentValues.isEmpty()){
                Class<?>[] paramTypes = new Class[argumentValues.getArgumentCount()];
                Object[] paramValues = new Object[argumentValues.getArgumentCount()];
                //对每一个参数，分数据类型进行处理
                for (int i = 0; i < argumentValues.getArgumentCount(); i++) {
                    ConstructorArgumentValue argumentValue = argumentValues.getIndexArgumentValue(i);
                    if("String".equals(argumentValue.getType())
                            ||"java.lang.String".equals(argumentValue.getType())){
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    }else if("Integer".equals(argumentValue.getType())
                            ||"java.lang.Integer".equals(argumentValue.getType())){
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
                    }else if("int".equals(argumentValue.getType())){
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
                    }else{
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    }
                }
                Constructor<?> constructor = clz.getConstructor(paramTypes);
                obj = constructor.newInstance(paramValues);
            }else{
                obj = clz.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(beanDefinition.getId()+" bean create. "+beanDefinition.getClassName()
                            +" : "+ obj.toString());
        return obj;
    }

    //对xml配置的处理
    private void handleProperties(BeanDefinition bd, Class<?> clz, Object obj){
        //处理属性
        System.out.println("handleProperties bean :"+bd.getId());
        //处理属性
        PropertyValues propertyValues = bd.getPropertyValues();
        if(!propertyValues.isEmpty()){
            for (int i = 0; i < propertyValues.size(); i++) {
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                String pType = propertyValue.getType();
                String pName = propertyValue.getName();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.getIsRef();
                Class<?>[] paramTypes = new Class<?>[1];
                Object[] paramValues = new Object[1];
                if(!isRef) {
                    if ("String".equals(pType) || "java.lang.String".equals(pType)) {
                        paramTypes[0] = String.class;
                    } else if ("Integer".equals(pType)
                            || "java.lang.Integer".equals(pType)) {
                        paramTypes[0] = Integer.class;
                    } else if ("int".equals(pType)) {
                        paramTypes[0] = int.class;
                    } else {
                        paramTypes[0] = String.class;
                    }

                    paramValues[0] = pValue;
                }else{
                    try {
                        paramTypes[0] = Class.forName(pType);
                        paramValues[0] = getBean((String) pValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //按照setXxxx规范查找setter方法，调用setter方法设置属性
                String methodName = "set"+pName.substring(0,1).toUpperCase()+pName.substring(1);
                Method method = null;
                try {
                    method = clz.getMethod(methodName,paramTypes);
                    method.invoke(obj,paramValues);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    public void refresh(){
        for (String beanName :
                beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean containsBean(String name) {
        return false;
    }

    @Override
    public void registerBean(String beanName, Object obj) {

    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class<?> getType(String name) {
        return null;
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {

    }

    @Override
    public void removeBeanDefinition(String name) {

    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return null;
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return false;
    }
}
