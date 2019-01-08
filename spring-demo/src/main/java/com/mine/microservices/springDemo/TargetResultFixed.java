package com.mine.microservices.springDemo;

import net.sf.cglib.proxy.FixedValue;

/**
 * @ClassName TargetResultFixed
 * @Description 锁定方法值  无论被代理的方法返回什么值 回调方法都返回固定值
 * @Author 刘海飞
 * @Date 2019/1/8 15:58
 * @Version 1.0
 **/
public class TargetResultFixed implements FixedValue{

    /**
     * 该类实现FixedValue接口，同时锁定回调值为999
     * (整型，CallbackFilter中定义的使用FixedValue型回调的方法为getConcreteMethodFixedValue，该方法返回值为整型)。
     */
    @Override
    public Object loadObject() throws Exception {
        System.out.println("锁定结果");
        Object result=999;
        return result;
    }
}
