package com.sonicge.beans.factory.ioc;

import com.sonicge.beans.factory.ioc.bean.Car;
import com.sonicge.beans.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class PrototypeBeanTest {
    @Test
    public void testPropertyBean(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:prototype-bean.xml");

        Car car1 = (Car) context.getBean("car");
        Car car2 = (Car) context.getBean("car");

        System.out.println(car1.equals(car2));
    }
}
