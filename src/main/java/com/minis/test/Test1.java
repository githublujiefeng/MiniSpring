package com.minis.test;

import com.minis.context.ClassPathXmlApplicationContext;

public class Test1 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
//        AServiceImpl aService = null;
//        try {
//            aService = (AServiceImpl) ctx.getBean("aservice");
//            aService.sayHello();
//        } catch (BeansException e) {
//            e.printStackTrace();
//        }

    }
}
