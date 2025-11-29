package com.sonicge.aop.framework;

import com.sonicge.aop.AdvicedSupport;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibAopProxy implements AopProxy {

    private final AdvicedSupport advicedSupport;

    public CglibAopProxy(AdvicedSupport advicedSupport) {
        this.advicedSupport = advicedSupport;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advicedSupport.getTargetSource().getClass());
        enhancer.setInterfaces(advicedSupport.getTargetSource().getTargetClasses());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advicedSupport));


        return enhancer.create();
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor {
        private final AdvicedSupport advicedSupport;

        public DynamicAdvisedInterceptor(AdvicedSupport advicedSupport) {
            this.advicedSupport = advicedSupport;
        }

        @Override
        public Object intercept(Object target, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
            CglibMethodInvocation cglibMethodInvocation = new CglibMethodInvocation(target, method, arguments, methodProxy);
            //判断当前代理的方法是否和expression表达式相符合！
            if(advicedSupport.getMethodMatcher().matches(method,advicedSupport.getTargetSource().getClass())){
                //代理方法
                return advicedSupport.getMethodInterceptor().invoke(cglibMethodInvocation);
            }
            return cglibMethodInvocation.proceed();
        }
    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy methodProxy) {
            super(target, method, arguments);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable{
            //其实这串代码就类似于  this.method.invoke(this.target,this.arguments); 只是拿MethodProxy封装起来了
            return this.methodProxy.invoke(this.target,this.getArguments());
        }
    }
}
