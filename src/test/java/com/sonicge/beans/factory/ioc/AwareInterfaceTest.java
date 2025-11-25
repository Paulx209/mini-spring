package com.sonicge.beans.factory.ioc;

import com.sonicge.beans.context.ApplicationContext;
import com.sonicge.beans.factory.BeanFactory;
import com.sonicge.beans.factory.ioc.bean.Car;
import com.sonicge.beans.factory.ioc.bean.People;
import com.sonicge.beans.factory.ioc.bean.service.HelloService;
import com.sonicge.beans.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class AwareInterfaceTest {
    @Test
    public void test() throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        HelloService helloService = (HelloService) context.getBean("helloService");
        ApplicationContext applicationContext = helloService.getApplicationContext();
        BeanFactory beanFactory = helloService.getBeanFactory();

        People people = (People) applicationContext.getBean("people");
        System.out.println(people);

        Car car = (Car) beanFactory.getBean("car");
        System.out.println(car);
    }

}
