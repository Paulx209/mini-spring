package com.sonicge.aop.framework;

import com.sonicge.aop.AdvicedSupport;
import com.sonicge.aop.TargetSource;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
    private final AdvicedSupport advicedSupport;

    public JdkDynamicAopProxy(AdvicedSupport advicedSupport) {
        this.advicedSupport = advicedSupport;
    }

    @Override
    public Object getProxy() {
        //需要目标类获得classLoader类加载器、共同的接口interfaces、invocationHandler（本身也实现了这个类）
        TargetSource targetSource = advicedSupport.getTargetSource();
        //和目标类相同的类加载器、和目标类相同的接口、InvocationHandler实现类 就是自己
        return Proxy.newProxyInstance(targetSource.getClassLoader(), targetSource.getTargetClasses(), this);
    }

    /**
     * invoke方法是实现InvocationHandler接口的，当调用目标方法的时候就会执行invoke方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //之前的invoke方法就是
        Object target = advicedSupport.getTargetSource().getTarget();
        Class<?> targetClass = target.getClass();
        Object retVal = null;
        //1.获取拦截器链
        List<Object> advisorChains = this.advicedSupport.getInterceptorAndDynamicInterceptionAdvice(method, targetClass);
        if(advisorChains == null || advisorChains.isEmpty()){
            return method.invoke(target,args);
        }else{
            //2.将拦截器链统一封装成ReflectiveMethodInvocation
            ReflectiveMethodInvocation reflectiveMethodInvocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClass, advisorChains);
            //3.执行拦截器链
            retVal = reflectiveMethodInvocation.proceed();
        }
        return retVal;
    }
}
