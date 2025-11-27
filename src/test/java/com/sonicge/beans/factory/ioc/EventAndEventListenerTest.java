package com.sonicge.beans.factory.ioc;

import com.sonicge.beans.factory.ioc.common.event.CustomEvent;
import com.sonicge.beans.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class EventAndEventListenerTest {
    @Test
    public void testEventListener() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:event-and-event-listener.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext));

        applicationContext.registerShutdownHook();//或者applicationContext.close()主动关闭容器;
    }
}
