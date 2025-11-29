package com.sonicge.aop;

import org.aopalliance.aop.Advice;

import java.lang.reflect.Method;

public interface AroundAdvice extends Advice {
    void beforeOfAround(Object target, Method method, Object[] args);

    void afterOfAround(Object target, Method method, Object[] args);
}
