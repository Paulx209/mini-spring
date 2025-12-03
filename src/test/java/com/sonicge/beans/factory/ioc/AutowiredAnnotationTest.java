package com.sonicge.beans.factory.ioc;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.factory.aop.bean.Car;
import com.sonicge.beans.factory.aop.bean.People;
import com.sonicge.beans.support.ClassPathXmlApplicationContext;
import org.junit.Test;

import java.beans.Beans;

public class AutowiredAnnotationTest {
    @Test
    public void testAutowiredAnnotation() throws BeansException{
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:autowired-annotation.xml");
        Car car = applicationContext.getBean(Car.class);
        System.out.println(car);
    }
}
