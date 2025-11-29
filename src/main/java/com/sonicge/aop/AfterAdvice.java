package com.sonicge.aop;

import org.aopalliance.aop.Advice;

import java.lang.reflect.Method;

public interface AfterAdvice extends Advice {
    void after(Object target, Method method, Object[] args);
}
