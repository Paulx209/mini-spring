package com.sonicge.beans.factory.ioc;

import com.sonicge.beans.factory.ioc.bean.People;
import com.sonicge.beans.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class ApplicationContextTest {
    @Test
    public void testApplicationContext() throws Exception{
        //1.这一步是会执行refresh()函数的，所有的单例bean都会被初始化，并且会执行到BeanFactoryPostProcessor 以及 BeanPostProcssor的方法
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        People people = (People) context.getBean("people");
        System.out.println(people);
    }
}
