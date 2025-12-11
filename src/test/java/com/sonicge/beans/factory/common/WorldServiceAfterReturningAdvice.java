package com.sonicge.beans.factory.common;

import com.sonicge.aop.AfterReturningService_v2;

import java.lang.reflect.Method;

public class WorldServiceAfterReturningAdvice  implements AfterReturningService_v2 {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] arguments, Object target) throws Throwable {
        System.out.println("AfterAdvice: do something after the earth explodes");
    }
}
