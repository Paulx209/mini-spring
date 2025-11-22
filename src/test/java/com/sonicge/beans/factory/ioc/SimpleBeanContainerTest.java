package com.sonicge.beans.factory.ioc;

import cn.hutool.core.io.IoUtil;
import com.sonicge.beans.PropertyValue;
import com.sonicge.beans.PropertyValues;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.beans.config.BeanReference;
import com.sonicge.beans.factory.ioc.bean.Car;
import com.sonicge.beans.factory.ioc.bean.People;
import com.sonicge.beans.factory.ioc.bean.service.HelloService;
import com.sonicge.beans.support.DefaultListableBeanFactory;
import com.sonicge.beans.xml.XmlBeanDefinitionReader;
import com.sonicge.core.io.DefaultResourceLoader;
import com.sonicge.core.io.Resource;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;


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
    public void testBeanFactory_v3() {
        String beanName = "helloService";
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        HelloService helloService = (HelloService) beanFactory.getBean(beanName);
        helloService.sayHello();
    }

    /**
     * 为Bean填充属性 （Bean实例化 + 属性赋值)
     */
    @Test
    public void testBeanFactory_v4() {
        String beanName = "helloService";
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        PropertyValue nameProperty = new PropertyValue("name", "sonicge");
        PropertyValue ageProperty = new PropertyValue("age", "21");
        PropertyValue isStudentProperty = new PropertyValue("isStudent", "true");
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(nameProperty);
        propertyValues.addPropertyValue(ageProperty);
        propertyValues.addPropertyValue(isStudentProperty);

        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class, propertyValues);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
        HelloService helloService = (HelloService) beanFactory.getBean(beanName);
        System.out.println(helloService);
    }

    /**
     * 处理Bean中存在引用对象属性！比如说People中有Car属性。通过BeanReference类来进行控制
     * 目前Bean的属性都是通过PropertyValue来控制的！因此如果是引用对象的话，就会判断name对应的value是否是BeanReference类型的。
     * 如果是的话，就会取出来其中的属性beanName，然后直接getBean了。
     */
    @Test
    public void testBeanFactory_v5() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //1.构造Car类的BeanDefinition定义类
        PropertyValue brandProperty = new PropertyValue("brand", "Benz");
        PropertyValues carPropertyValues = new PropertyValues();
        carPropertyValues.addPropertyValue(brandProperty);
        BeanDefinition carBeanDefinition = new BeanDefinition(Car.class, carPropertyValues);

        //2.构造People类的BeanDefinition定义类
        PropertyValue nameProperty = new PropertyValue("name", "sonicge");
        PropertyValue ageProperty = new PropertyValue("age", "21");
        PropertyValue isStudentProperty = new PropertyValue("isStudent", "true");
        BeanReference carBeanReference = new BeanReference("car");
        PropertyValue carBeanReferenceProperty = new PropertyValue("car", carBeanReference);
        PropertyValues peoplepPropertyValues = new PropertyValues();
        peoplepPropertyValues.addPropertyValue(nameProperty);
        peoplepPropertyValues.addPropertyValue(ageProperty);
        peoplepPropertyValues.addPropertyValue(isStudentProperty);
        peoplepPropertyValues.addPropertyValue(carBeanReferenceProperty);
        BeanDefinition peopleBeanDefinition = new BeanDefinition(People.class, peoplepPropertyValues);

        //3.注册BeanDefinition到bean定义表中。用于getBean
        beanFactory.registerBeanDefinition("people", peopleBeanDefinition);
        beanFactory.registerBeanDefinition("car", carBeanDefinition);

        //4.beanFactory获取Bean
        People people = (People) beanFactory.getBean("people");
        Car car = people.getCar();
        System.out.println(car);
    }

    /**
     * 测试资源类和资源加载器类
     */
    @Test
    public void testBeanFactory_v6() {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        //加载classpath下的资源
        try {
            Resource resource = resourceLoader.getResource("classpath:hello.txt");
            InputStream inputStream = resource.getInputStream();
            String text = IoUtil.readUtf8(inputStream);
            System.out.println(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //加载文件系统资源
        try {
            Resource resource = resourceLoader.getResource("src/test/resources/hello.txt");
            InputStream inputStream = resource.getInputStream();
            String text = IoUtil.readUtf8(inputStream);
            System.out.println(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //加载url资源
        try {
            Resource resource = resourceLoader.getResource("https://www.baidu.com");
            InputStream inputStream = resource.getInputStream();
            String text = IoUtil.readUtf8(inputStream);
            System.out.println(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test(){
        try {
            String className = "com.sonicge.beans.factory.ioc.bean.People";
            Class<?> clazz = Class.forName(className);
            System.out.println(clazz.getName());
            System.out.println(clazz.getSimpleName());
            Field[] declaredFields = clazz.getDeclaredFields();
            for(Field field : declaredFields){
                System.out.println(field.getName());
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 测试解析xml配置文件中的属性，配置成BeanDefinition，不再手动创建BeanDefinition
     */
    @Test
    public void testBeanFactory_v7(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        //1.解析xml文件 -> 封装成对应的BeanDefinition
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");
        //2.获取Bean
        People people = (People) beanFactory.getBean("people");
        System.out.println(people);

        Car car = (Car) beanFactory.getBean("car");
        System.out.println(car);
    }

}
