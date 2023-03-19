package com.minis.test;

import com.minis.ClassPathXmlApplicationContext;

public class Test1 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        AServiceImpl aService = (AServiceImpl) ctx.getBean("aservice");
        aService.sayHello();
    }
}
