package com.sonicge.beans.factory.ioc;

import com.sonicge.beans.context.ApplicationContext;
import com.sonicge.beans.factory.bean.A;
import com.sonicge.beans.factory.bean.B;
import com.sonicge.beans.support.ClassPathXmlApplicationContext;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CircularReferenceWithProxyBeanTest {
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:circular-reference-with-proxy-bean.xml");
        A a = (A) context.getBean("a",A.class);
        B b = (B) context.getBean("b",B.class);

        System.out.println(b.getA());
        System.out.println(a);
        System.out.println(a.getB());
        System.out.println( b == a.getB());
        assertThat(b.getA() != a).isTrue();
    }
}
