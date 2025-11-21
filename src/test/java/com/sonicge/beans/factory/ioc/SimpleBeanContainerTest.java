package com.sonicge.beans.factory.ioc;

import com.sonicge.beans.PropertyValue;
import com.sonicge.beans.PropertyValues;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.beans.factory.service.HelloService;
import com.sonicge.beans.support.DefaultListableBeanFactory;
import org.junit.Test;

public class SimpleBeanContainerTest {
    @Test
    public void testBeanFactory_v1() {
//        BeanFactory beanFactory = new BeanFactory();
//        UserService userService = new UserService();
//        beanFactory.registerBean("userService",userService);
//        UserService service = (UserService) beanFactory.getBean("userService");
//        System.out.println(service);
    }

    @Test
    public void testBeanFactory_v2() {
//        String beanName = "helloService";
//        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//        //创建BeanDefinition对象
//        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
//        //将BeanDefinition对象注册到beanDefinitionMap中（DefaultListableBeanFactory类的属性）
//        beanFactory.registerBeanDefinition(beanName, beanDefinition);
//        //注册好了之后，就可以调用getBean方法（AbstractBeanFactory父类中的，部分的实现还要依赖于AbstractAutowireCapableBeanFactory）
//        HelloService helloService = (HelloService) beanFactory.getBean(beanName);
//        //调用方法。。。
//        String returnValue = helloService.sayHello();
//        System.out.println(returnValue);
    }

    /**
     * Bean实例化策略InstantiationStrategy
     */
    @Test
    public void testBeanFactory_v3(){
        String beanName = "helloService";
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
        beanFactory.registerBeanDefinition(beanName,beanDefinition);

        HelloService helloService = (HelloService) beanFactory.getBean(beanName);
        helloService.sayHello();
    }

    /**
     * 为Bean填充属性 （Bean实例化 + 属性赋值)
     */
    @Test
    public void testBeanFactory_v4(){
        String beanName = "helloService";
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        PropertyValue nameProperty = new PropertyValue("name","sonicge");
        PropertyValue ageProperty = new PropertyValue("age","21");
        PropertyValue isStudentProperty = new PropertyValue("isStudent","true");
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(nameProperty);
        propertyValues.addPropertyValue(ageProperty);
        propertyValues.addPropertyValue(isStudentProperty);

        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class,propertyValues);
        beanFactory.registerBeanDefinition(beanName,beanDefinition);
        HelloService bean = (HelloService) beanFactory.getBean(beanName);
        System.out.println(bean);
    }
}
