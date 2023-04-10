package com.minis.beans.factory.support;

//public class SimpleBeanFactory implements BeanFactory {
public class SimpleBeanFactory  {
/*    private List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
    private List<String> beanNames = new ArrayList<String>();
    private Map<String, Object> singletons = new HashMap<String, Object>();

    public SimpleBeanFactory() {
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

    public Boolean containsBean(String name) {
        return singletons.containsKey(name);
    }

    public void registerBean(String beanName, Object obj) {

    }

    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.add(beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }*/
}
