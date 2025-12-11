package com.sonicge.aop.adapter;

import com.sonicge.aop.AfterAdvice_v2;
import com.sonicge.aop.AfterReturningService_v2;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AfterReturningAdviceInterceptor implements MethodInterceptor, AfterAdvice_v2 {

    private AfterReturningService_v2 advice;

    public AfterReturningAdviceInterceptor() {
    }

    public AfterReturningAdviceInterceptor(AfterReturningService_v2 advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object retVal = methodInvocation.proceed();
        this.advice.afterReturning(retVal,methodInvocation.getMethod(),methodInvocation.getArguments(),methodInvocation.getThis());
        return retVal;
    }
}
