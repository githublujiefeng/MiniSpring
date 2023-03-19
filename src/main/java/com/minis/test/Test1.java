package com.minis.test;

import com.minis.context.ClassPathXmlApplicationContext_bak;

public class Test1 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext_bak ctx = new ClassPathXmlApplicationContext_bak("beans.xml");
        AServiceImpl aService = (AServiceImpl) ctx.getBean("aservice");
        aService.sayHello();
    }
}
