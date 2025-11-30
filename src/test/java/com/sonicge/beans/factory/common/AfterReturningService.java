package com.sonicge.beans.factory.common;

import com.sonicge.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class AfterReturningService implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object target, Method method, Object[] args) {
        System.out.println("正常执行完毕后增强");
    }
}
