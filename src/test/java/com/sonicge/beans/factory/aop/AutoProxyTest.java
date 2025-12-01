package com.sonicge.beans.factory.aop;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.context.ApplicationContext;
import com.sonicge.beans.factory.service.WorldService;
import com.sonicge.beans.support.ClassPathXmlApplicationContext;
import org.junit.Test;

import java.util.Map;

public class AutoProxyTest {
    @Test
    public void testAutoProxy() throws BeansException {
        //testAutoProxy()方法逻辑

        //1.创建Ioc容器
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:auto-proxy.xml");

        Map<String, WorldService> beansOfType = context.getBeansOfType(WorldService.class);
        for (WorldService worldService : beansOfType.values()) {
            System.out.println(worldService);
        }

        //2.从Ioc容器中获得DefaultAdvisorAutoProxyCreator类

        WorldService worldService = context.getBean("worldService", WorldService.class);
        System.out.println(worldService);

        //3.然后获取WorldService？

        worldService.explode();

        //额外：记得验证这些代理类是否在singletonMap中

    }
}
