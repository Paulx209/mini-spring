package com.sonicge.beans.factory.expanding;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.context.ApplicationContext;
import com.sonicge.beans.factory.aop.bean.Car;
import com.sonicge.beans.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class PropertyPlaceholderConfigurerTest {
    @Test
    public void test() throws BeansException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:property-placeholder-configurer.xml");

        Car car = (Car) context.getBean("car");
        String brand = car.getBrand();
        System.out.println(brand);
    }
}
