package com.sonicge.beans.factory.common;

import com.sonicge.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class WorldServiceBeforeService implements MethodBeforeAdvice {
    @Override
    public void before(Object target, Method method, Object[] args) {
        System.out.println("前置增强方法！！！");
    }
}
