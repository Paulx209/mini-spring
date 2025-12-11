package com.sonicge.aop.adapter;

import com.sonicge.aop.BeforeAdvice;
import com.sonicge.aop.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MethodBeforeAdviceInterceptor implements MethodInterceptor, BeforeAdvice {
    private MethodBeforeAdvice beforeAdvice;

    public MethodBeforeAdviceInterceptor(){}

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice beforeAdvice) {
        this.beforeAdvice = beforeAdvice;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        this.beforeAdvice.before(mi.getThis(),mi.getMethod(),mi.getArguments());
        return mi.proceed();
    }
}
