package com.sonicge.beans.factory.aop;

import com.sonicge.aop.AdvicedSupport;
import com.sonicge.aop.MethodMatcher;
import com.sonicge.aop.TargetSource;
import com.sonicge.aop.aspectj.AspectJExpressionPointcut;
import com.sonicge.aop.framework.CglibAopProxy;
import com.sonicge.aop.framework.JdkDynamicAopProxy;
import com.sonicge.beans.factory.common.WorldServiceInterceptor;
import com.sonicge.beans.factory.service.WorldService;
import com.sonicge.beans.factory.service.WorldServiceImpl;
import org.junit.Test;

public class DynamicProxyTest {

    private AdvicedSupport advicedSupport;

    @Test
    public void test() {
        //创建worldService类，构建targetSource类
        WorldService worldService = new WorldServiceImpl();
        TargetSource targetSource = new TargetSource(worldService);

        //构建MethodInterceptor类
        WorldServiceInterceptor worldServiceInterceptor = new WorldServiceInterceptor();

        //构建MethodMatcher类  -> AspectJExpressionPointcut
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut("execution(* com.sonicge.beans.factory.service.WorldService.*(..))");

        //创建AdvicedSupport类
        AdvicedSupport advicedSupport = new AdvicedSupport();
        advicedSupport.setTargetSource(targetSource);
        advicedSupport.setMethodInterceptor(worldServiceInterceptor);
        advicedSupport.setMethodMatcher(aspectJExpressionPointcut);

        //创建JdkDynamicAopProxy类 获取代理类
        JdkDynamicAopProxy jdkDynamicAopProxy = new JdkDynamicAopProxy(advicedSupport);
        WorldService proxy = (WorldService) jdkDynamicAopProxy.getProxy();
        proxy.explode();


        //TargetSource , WorldServiceInterceptor ,AspectJExpressionPointcut  -> AdvicedSupport -> JdkDynamicAopProxy

        //以前的增强类代码都直接写到InvocationHandler，也就是JdkDynamicAopProxy里面。现在封装到MethodInterceptor里面，不直接暴露出来

        //AspectJExpressionPointcut -> methodMatcher类
    }

    @Test
    public void testCglibDynamicProxy() {
        //创建worldService类，构建targetSource类
        WorldService worldService = new WorldServiceImpl();
        TargetSource targetSource = new TargetSource(worldService);

        //构建MethodInterceptor类
        WorldServiceInterceptor worldServiceInterceptor = new WorldServiceInterceptor();

        //构建MethodMatcher类  -> AspectJExpressionPointcut
        MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* com.sonicge.beans.factory.service..*(..))").getMethodMatcher();

        //创建AdvicedSupport类
        AdvicedSupport advicedSupport = new AdvicedSupport();
        advicedSupport.setTargetSource(targetSource);
        advicedSupport.setMethodInterceptor(worldServiceInterceptor);
        advicedSupport.setMethodMatcher(methodMatcher);

        WorldServiceImpl proxy = (WorldServiceImpl) new CglibAopProxy(advicedSupport).getProxy();
        proxy.explode();
    }
}
