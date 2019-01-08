package com.mine.microservices.springDemo;

import net.sf.cglib.proxy.Enhancer;

/**
 * @ClassName TestCglib
 * @Description TODO
 * @Author 刘海飞
 * @Date 2019/1/8 15:01
 * @Version 1.0
 **/
public class TestCglib {

    public static void main(String[] args) {
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        enhancer.setCallback(new TargetInterceptor());
        TargetObject targetObject=(TargetObject)enhancer.create();

        System.out.println(targetObject);
        System.out.println(targetObject.method1("mmm"));
        System.out.println(targetObject.method2(100));
        System.out.println(targetObject.method3(200));



    }
}
