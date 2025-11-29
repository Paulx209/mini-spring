package com.sonicge.aop;

import org.aopalliance.aop.Advice;

import java.lang.reflect.Method;

public interface AfterReturningAdvice extends Advice {
    void afterReturning(Object target, Method method, Object[] args);
}
