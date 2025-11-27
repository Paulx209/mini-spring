package com.sonicge.beans.factory.ioc;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.factory.ioc.common.event.CustomEvent;
import com.sonicge.beans.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class EventAndEventListenerTest {
    @Test
    public void testEventListener() throws BeansException{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:event-and-event-listener.xml");

        context.publishEvent(new CustomEvent(context));

        context.registerShutdownHook();
    }
}
