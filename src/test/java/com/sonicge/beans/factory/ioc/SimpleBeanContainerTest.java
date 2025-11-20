package com.sonicge.beans.factory.ioc;

import com.sonicge.beans.factory.BeanFactory;
import com.sonicge.beans.factory.service.UserService;
import org.junit.Test;

public class SimpleBeanContainerTest {
    @Test
    public void testBeanFactory(){
        BeanFactory beanFactory = new BeanFactory();
        UserService userService = new UserService();
        beanFactory.registerBean("userService",userService);
        UserService service = (UserService) beanFactory.getBean("userService");
        System.out.println(service);
    }
}
