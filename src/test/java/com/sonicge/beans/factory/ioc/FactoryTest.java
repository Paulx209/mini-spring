package com.sonicge.beans.factory.ioc;

import com.sonicge.beans.context.ApplicationContext;
import com.sonicge.beans.factory.ioc.bean.Car;
import com.sonicge.beans.support.ClassPathXmlApplicationContext;
import org.junit.Test;

import java.net.CacheRequest;

public class FactoryTest {
    @Test
    public void testFactoryBean() throws Exception{
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:factory-bean.xml");
        Car car = (Car) context.getBean("catFactoryBean");
        System.out.println(car);
    }
}
