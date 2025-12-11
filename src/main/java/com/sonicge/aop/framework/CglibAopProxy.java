package com.sonicge.aop.framework;

import com.sonicge.aop.AdvicedSupport;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.List;

public class CglibAopProxy implements AopProxy {

    private final AdvicedSupport advicedSupport;

    public CglibAopProxy(AdvicedSupport advicedSupport) {
        this.advicedSupport = advicedSupport;
    }

    @Override
    public Object getProxy() {
        //创建动态代理类
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advicedSupport.getTargetSource().getTarget().getClass());
        enhancer.setInterfaces(advicedSupport.getTargetSource().getTargetClasses());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advicedSupport));


        return enhancer.create();
    }

    /**
     * 该类类似于JDK中的InvocationHandler的实现类，其中intercept方法就是对方法的拦截增强！
     */
    private static class DynamicAdvisedInterceptor implements MethodInterceptor {
        private final AdvicedSupport advicedSupport;

        public DynamicAdvisedInterceptor(AdvicedSupport advicedSupport) {
            this.advicedSupport = advicedSupport;
        }

        @Override
        public Object intercept(Object proxy, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
            //获取目标对象
            Object target = advicedSupport.getTargetSource().getTarget();
            Class<?> targetClass = target.getClass();
            Object retVal = null;

            //获取到所有的增强
            List<Object> advisorChains = this.advicedSupport.getInterceptorAndDynamicInterceptionAdvice(method, targetClass);
            //封装成ReflectiveMethodInvocation类，在这个类中会实现调用拦截器链逻辑
            CglibMethodInvocation cglibMethodInvocation = new CglibMethodInvocation(proxy,target,method,arguments,targetClass,advisorChains,methodProxy);
            if (advisorChains == null || advisorChains.isEmpty()) {
                retVal = methodProxy.invoke(target, arguments);
            } else {
                retVal = cglibMethodInvocation.proceed();
            }
            return retVal;
        }
    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object proxy,Object target, Method method, Object[] arguments, Class<?> targetClass,List<Object> interceptorsAndDynamicMethodMatchers,MethodProxy methodProxy) {
            super(proxy,target, method, arguments,targetClass,interceptorsAndDynamicMethodMatchers);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            //其实这串代码就类似于  this.method.invoke(this.target,this.arguments); 只是拿MethodProxy封装起来了
            return super.proceed();
        }
    }
}
