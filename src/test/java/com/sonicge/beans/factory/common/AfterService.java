package com.sonicge.beans.factory.common;

import com.sonicge.aop.AfterAdvice;

import java.lang.reflect.Method;

public class AfterService implements AfterAdvice {
    @Override
    public void after(Object target, Method method, Object[] args) {
        System.out.println("最终通知 ---  增强");
    }
}
