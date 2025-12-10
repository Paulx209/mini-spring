package com.sonicge.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectiveMethodInvocation implements MethodInvocation {
    protected final Object proxy;

    protected final Object target;

    protected final Method method;

    protected final Object[] arguments;

    protected final Class<?> targetClass;

    protected final List<Object> interceptorsAndDynamicMethodMatchers;

    protected int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(Object proxy,Object target, Method method, Object[] arguments,Class<?> targetClass,List<Object> interceptorsAndDynamicMethodMatchers) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.targetClass = targetClass;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public Object proceed() throws Throwable {
        //如果当前的下标执行到拦截器链的最后一个增强时
        if(this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size()-1){
            //当调用次数 = 拦截器个数时  -> 执行当前的method方法
            return method.invoke(target,arguments);
        }
        //如果调用次数 < 拦截器个数 即拦截器还没有执行完时 -> 执行拦截器的方法
        Object interceptorOrInterceptorAdvice = this.interceptorsAndDynamicMethodMatchers.get(++currentInterceptorIndex);
        return ((MethodInterceptor) interceptorOrInterceptorAdvice).invoke(this);
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}
