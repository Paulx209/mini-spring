package com.sonicge.aop;

import org.aopalliance.aop.Advice;

import java.lang.reflect.Method;

public interface AfterThrowingAdvice extends Advice {
    void afterThrowing(Object target, Method method, Object[] args);
}
