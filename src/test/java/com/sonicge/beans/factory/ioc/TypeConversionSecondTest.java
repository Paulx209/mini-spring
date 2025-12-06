package com.sonicge.beans.factory.ioc;

import com.sonicge.beans.context.ApplicationContext;
import com.sonicge.beans.factory.bean.Car;
import com.sonicge.beans.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class TypeConversionSecondTest {
    @Test
    public void testConversionService(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:type-conversion-second-part.xml");
        Car car = context.getBean("car", Car.class);
        System.out.println(car);
    }
}
