package com.sonicge.beans.factory.ioc;

import com.sonicge.beans.factory.aop.bean.Car;
import com.sonicge.beans.support.ClassPathXmlApplicationContext;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ValueAnnotationTest {
    @Test
    public void test(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:value-annotation.xml");

        Car car = applicationContext.getBean("car", Car.class);
        assertThat(car.getBrand()).isEqualTo("lamborghini");
    }
}
