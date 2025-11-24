package com.sonicge.beans.factory.ioc;

import com.sonicge.beans.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class InitAndDestroyMethodTest {

    @Test
    public void testInitAndDestroyMethod() throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:init-and-destroy-method.xml");
        context.registerShutdownHook();
    }

}
