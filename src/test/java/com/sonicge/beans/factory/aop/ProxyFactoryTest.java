package com.sonicge.beans.factory.aop;

import com.sonicge.aop.TargetSource;
import com.sonicge.aop.adapter.AfterReturningAdviceInterceptor;
import com.sonicge.aop.adapter.MethodBeforeAdviceInterceptor;
import com.sonicge.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.sonicge.aop.framework.ProxyFactory;
import com.sonicge.beans.context.ApplicationContext;
import com.sonicge.beans.factory.common.WorldServiceAfterReturningAdvice;
import com.sonicge.beans.factory.common.WorldServiceBeforeAdvice;
import com.sonicge.beans.factory.service.WorldService;
import com.sonicge.beans.factory.service.WorldServiceImpl;
import com.sonicge.beans.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class ProxyFactoryTest {
    @Test
    public void testAdvisor() throws Exception {
        WorldService worldService = new WorldServiceImpl();

        String expression = "execution(* com.sonicge.beans.factory.service.WorldService.*(..))";
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(expression);
        MethodBeforeAdviceInterceptor beforeService = new MethodBeforeAdviceInterceptor(new WorldServiceBeforeAdvice());
        advisor.setAdvice(beforeService);

        AspectJExpressionPointcutAdvisor advisor1 = new AspectJExpressionPointcutAdvisor();
        advisor1.setExpression(expression);
        AfterReturningAdviceInterceptor afterReturningAdvice = new AfterReturningAdviceInterceptor(new WorldServiceAfterReturningAdvice());
        advisor1.setAdvice(afterReturningAdvice);

        TargetSource targetSource = new TargetSource(worldService);

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(advisor);
        proxyFactory.addAdvisor(advisor1);
        proxyFactory.setTargetSource(targetSource);
        proxyFactory.setProxyTargetClass(false);
        WorldService proxy = (WorldService) proxyFactory.getProxy();
        proxy.explode();
    }

    @Test
    public void testProxy(){
        ApplicationContext context =new ClassPathXmlApplicationContext("classpath:");
    }

}
