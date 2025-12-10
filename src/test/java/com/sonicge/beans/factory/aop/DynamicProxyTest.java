package com.sonicge.beans.factory.aop;

import com.sonicge.aop.*;
import com.sonicge.aop.aspectj.AspectJExpressionPointcut;
import com.sonicge.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.sonicge.aop.framework.CglibAopProxy;
import com.sonicge.aop.framework.JdkDynamicAopProxy;
import com.sonicge.aop.framework.ProxyFactory;
import com.sonicge.beans.factory.common.*;
import com.sonicge.beans.factory.service.WorldService;
import com.sonicge.beans.factory.service.WorldServiceImpl;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Test;

import java.lang.annotation.Target;

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

    @Test
    public void testProxyFactory() {
        //使用动态代理

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

        advicedSupport.setProxyTargetClass(false);
        WorldService jdkProxy = (WorldService) new ProxyFactory().getProxy();
        jdkProxy.explode();


        advicedSupport.setProxyTargetClass(true);
        WorldService cglibProxy = (WorldService) new ProxyFactory().getProxy();
        cglibProxy.explode();

    }

    @Test
    public void testBeforeAdvice() {

        WorldServiceBeforeService beforeAdviceService = new WorldServiceBeforeService();
        AfterService afterService = new AfterService();
        AroundService aroundService = new AroundService();
        AfterThrowingService afterThrowingService = new AfterThrowingService();
        AfterReturningService afterReturningService = new AfterReturningService();
        //这个东西是主要的增强方法，其中主要是invoke()方法里面执行的内容
        GenericInterceptor adviceInterceptor = new GenericInterceptor();
        adviceInterceptor.setBeforeAdvice(beforeAdviceService);
        adviceInterceptor.setAfterAdvice(afterService);
        adviceInterceptor.setAroundAdvice(aroundService);
        adviceInterceptor.setAfterThrowingAdvice(afterThrowingService);
        adviceInterceptor.setAfterReturningAdvice(afterReturningService);

        WorldService worldService = new WorldServiceImpl();
        AdvicedSupport advicedSupport = new AdvicedSupport();
        advicedSupport.setTargetSource(new TargetSource(worldService));
        advicedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* com.sonicge.beans.factory.service..*(..))"));
        advicedSupport.setMethodInterceptor(adviceInterceptor);

        ProxyFactory proxyFactory = new ProxyFactory();
        WorldService service = (WorldService) proxyFactory.getProxy();
        service.explode();
    }

    @Test
    public void tesAdvisor() {
        WorldServiceBeforeService beforeAdviceService = new WorldServiceBeforeService();
        AfterService afterService = new AfterService();
        AroundService aroundService = new AroundService();
        AfterThrowingService afterThrowingService = new AfterThrowingService();
        AfterReturningService afterReturningService = new AfterReturningService();
        //这个东西是主要的增强方法，其中主要是invoke()方法里面执行的内容
        GenericInterceptor adviceInterceptor = new GenericInterceptor();
        adviceInterceptor.setBeforeAdvice(beforeAdviceService);
        adviceInterceptor.setAfterAdvice(afterService);
        adviceInterceptor.setAroundAdvice(aroundService);
        adviceInterceptor.setAfterThrowingAdvice(afterThrowingService);
        adviceInterceptor.setAfterReturningAdvice(afterReturningService);


        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        String expression = "execution(* com.sonicge.beans.factory.service..*(..))";
        advisor.setAdvice(adviceInterceptor);
        advisor.setExpression(expression);

        ClassFilter classFilter = advisor.getPointcut().getClassFilter();
        if (classFilter.matches(WorldService.class)) {
            //如果匹配的话
            AdvicedSupport support = new AdvicedSupport();
            support.setTargetSource(new TargetSource(new WorldServiceImpl()));
            support.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            support.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());

            WorldService  service = (WorldService ) new ProxyFactory().getProxy();
            service.explode();
        }


    }

}
