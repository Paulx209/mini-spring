package com.sonicge.beans.factory.common;

import com.sonicge.aop.AfterThrowingAdvice;

import java.lang.reflect.Method;

public class AfterThrowingService implements AfterThrowingAdvice {
    @Override
    public void afterThrowing(Object target, Method method, Object[] args) {
        System.out.println("异常时增强。。。");
    }
}
