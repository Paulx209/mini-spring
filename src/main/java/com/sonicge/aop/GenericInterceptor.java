package com.sonicge.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class GenericInterceptor implements MethodInterceptor {
    private MethodBeforeAdvice beforeAdvice;

    private AfterReturningAdvice afterReturningAdvice;

    private AfterAdvice afterAdvice;

    private AfterThrowingAdvice afterThrowingAdvice;

    private AroundAdvice aroundAdvice;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object target = methodInvocation.getThis();
        Method method = methodInvocation.getMethod();
        Object[] arguments = methodInvocation.getArguments();
        Object retValue = null;
        try {
            //1.Around的前置
            if (aroundAdvice != null) {
                aroundAdvice.beforeOfAround(target, method, arguments);
            }
            //2.Before通知
            if (beforeAdvice != null) {
                beforeAdvice.before(target, method, arguments);
            }
            //3.目标代码执行
            retValue = method.invoke(target, arguments);

            //4.AfterReturning 通知
            if (afterReturningAdvice != null) {
                afterReturningAdvice.afterReturning(target, method, arguments);
            }
            //5.Around的后置
            if (aroundAdvice != null) {
                aroundAdvice.afterOfAround(target, method, arguments);
            }

        } catch (Exception e) {
            //AfterThrowing 通知
            if (afterThrowingAdvice != null) {
                afterThrowingAdvice.afterThrowing(target, method, arguments);
            }
            //Around的后置 (抛出异常也会执行..)
            if (aroundAdvice != null) {
                aroundAdvice.afterOfAround(target, method, arguments);
            }
            throw e;
        } finally {
            //6.After通知
            if (afterAdvice != null) {
                afterAdvice.after(target, method, arguments);
            }
        }
        return retValue;
    }


    public void setAfterReturningAdvice(AfterReturningAdvice afterReturningAdvice) {
        this.afterReturningAdvice = afterReturningAdvice;
    }


    public void setBeforeAdvice(MethodBeforeAdvice beforeAdvice) {
        this.beforeAdvice = beforeAdvice;
    }


    public void setAfterAdvice(AfterAdvice afterAdvice) {
        this.afterAdvice = afterAdvice;
    }


    public void setAfterThrowingAdvice(AfterThrowingAdvice afterThrowingAdvice) {
        this.afterThrowingAdvice = afterThrowingAdvice;
    }

}
