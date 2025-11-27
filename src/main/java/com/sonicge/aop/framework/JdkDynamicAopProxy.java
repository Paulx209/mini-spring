package com.sonicge.aop.framework;

import com.sonicge.aop.AdvicedSupport;
import com.sonicge.aop.MethodMatcher;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
    private final AdvicedSupport advicedSupport;

    public JdkDynamicAopProxy(AdvicedSupport advicedSupport) {
        this.advicedSupport = advicedSupport;
    }

    @Override
    public Object getProxy() {
        //1.和目标类相同的类加载器
        ClassLoader classLoader = advicedSupport.getTargetSource().getTargetClasses().getClassLoader();
        //2.和目标类相同的接口
        Class<?>[] interfaces = advicedSupport.getTargetSource().getTargetClasses().getInterfaces();
        //3.InvocationHandler实现类 就是自己
        return Proxy.newProxyInstance(classLoader, interfaces, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object target = advicedSupport.getTargetSource().getTarget();
        MethodMatcher methodMatcher = advicedSupport.getMethodMatcher();
        boolean isMatch = methodMatcher.matches(method, target.getClass());
        if(isMatch){
            MethodInterceptor methodInterceptor = advicedSupport.getMethodInterceptor();
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(target,method,args));
        }
        return method.invoke(target,method,args);
    }
}
