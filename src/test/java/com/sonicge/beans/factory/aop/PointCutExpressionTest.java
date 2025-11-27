package com.sonicge.beans.factory.aop;

import com.sonicge.aop.aspectj.AspectJExpressionPointcut;
import com.sonicge.beans.factory.aop.bean.Car;
import com.sonicge.beans.factory.aop.bean.service.HelloService;
import org.junit.Test;

import java.lang.reflect.Method;


public class PointCutExpressionTest {
    @Test
    public void testPointCutExpression()throws Exception{
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut("execution(* com.sonicge.beans.factory.aop.bean.service.HelloService.*(..))");
        boolean matches = aspectJExpressionPointcut.matches(Car.class);
        System.out.println(matches);

        boolean matches1 = aspectJExpressionPointcut.matches(HelloService.class);
        System.out.println(matches1);

        Class<?> clazz = Class.forName("com.sonicge.beans.factory.aop.bean.service.HelloService");
        Method method = clazz.getDeclaredMethod("sayHello");
        boolean matches2 = aspectJExpressionPointcut.matches(method, clazz);
        System.out.println(matches2);

    }
}
