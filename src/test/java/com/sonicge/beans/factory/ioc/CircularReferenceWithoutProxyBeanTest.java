package com.sonicge.beans.factory.ioc;

import com.sonicge.beans.context.ApplicationContext;
import com.sonicge.beans.factory.bean.A;
import com.sonicge.beans.factory.bean.B;
import com.sonicge.beans.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class CircularReferenceWithoutProxyBeanTest {
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:cicular-reference-without-proxy-bean.xml");
        A a = (A) context.getBean("a");
        B b = (B) context.getBean("b");
        System.out.println(a);
        System.out.println(b);
    }
}
