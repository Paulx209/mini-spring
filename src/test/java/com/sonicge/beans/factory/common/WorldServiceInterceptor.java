package com.sonicge.beans.factory.common;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;



public class WorldServiceInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        //执行前增强
        System.out.println("Do something before the earth explodes");
        //执行原方法
        Object result = methodInvocation.proceed();
        //执行后增强
        System.out.println("Do something after the earth explodes");
        return result;
    }
}
