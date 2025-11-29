package com.sonicge.aop.framework.adapter;

import com.sonicge.aop.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MethodBeforeAdviceInterceptor implements MethodInterceptor {
    private MethodBeforeAdvice beforeAdvice;

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice beforeAdvice){
        this.beforeAdvice = beforeAdvice;
    }
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        //在执行被代理方法之前，先执行before方法
        beforeAdvice.before(methodInvocation.getThis(),methodInvocation.getMethod(),methodInvocation.getArguments());
        return methodInvocation.proceed();
    }
}
