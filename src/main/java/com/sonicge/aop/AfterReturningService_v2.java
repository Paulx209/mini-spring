package com.sonicge.aop;

import java.lang.reflect.Method;

public interface AfterReturningService_v2 extends AfterAdvice_v2{
    void afterReturning(Object returnValue, Method method,Object[] arguments,Object target) throws  Throwable;
}
