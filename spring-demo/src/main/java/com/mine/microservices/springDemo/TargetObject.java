package com.mine.microservices.springDemo;

/**
 * @ClassName TargetObject
 * @Description TODO
 * @Author 刘海飞
 * @Date 2019/1/8 14:55
 * @Version 1.0
 **/
public class TargetObject {

    public String method1(String paramName) {
        return paramName;
    }

    public int method2(int count) {
        return count;
    }

    public int method3(int count) {
        return count;
    }

    @Override
    public String toString() {
        return "TargetObject []"+ getClass();
    }
}
