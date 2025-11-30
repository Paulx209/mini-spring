package com.sonicge.beans.factory.common;

import com.sonicge.aop.AroundAdvice;

import java.lang.reflect.Method;

public class AroundService implements AroundAdvice {
    @Override
    public void beforeOfAround(Object target, Method method, Object[] args) {
        System.out.println("Around的前置增强");
    }

    @Override
    public void afterOfAround(Object target, Method method, Object[] args) {
        System.out.println("Around的后置增强");
    }
}
