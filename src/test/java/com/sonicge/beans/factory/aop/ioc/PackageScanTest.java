package com.sonicge.beans.factory.aop.ioc;

import com.sonicge.beans.context.ApplicationContext;
import com.sonicge.beans.factory.aop.bean.Car;
import com.sonicge.beans.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class PackageScanTest {

    @Test
    public void testComponent(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:package-scan.xml");
        Car car = context.getBean("car", Car.class);
        System.out.println(car);
    }
}
