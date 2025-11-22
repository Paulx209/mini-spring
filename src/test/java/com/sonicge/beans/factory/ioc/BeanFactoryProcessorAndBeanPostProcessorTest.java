package com.sonicge.beans.factory.ioc;


import com.sonicge.beans.factory.ioc.bean.People;
import com.sonicge.beans.factory.ioc.common.CustomBeanFactoryPostProcessor;
import com.sonicge.beans.factory.ioc.common.CustomerBeanPostProcessor;
import com.sonicge.beans.support.DefaultListableBeanFactory;
import com.sonicge.beans.xml.XmlBeanDefinitionReader;
import com.sonicge.core.io.DefaultResourceLoader;
import com.sonicge.core.io.Resource;
import com.sonicge.core.io.ResourceLoader;
import org.junit.Test;

public class BeanFactoryProcessorAndBeanPostProcessorTest {

    /**
     * 测试BeanFactoryPostProcessor类
     * 在类的实例化之前修改BeanDefinition类信息
     * @throws Exception
     */
    @Test
    public void testBeanFactoryPostProcessor() throws  Exception{
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        //从xml配置文件中加载所有的BeanDefinition类。
        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        //在所有的BeanDefinition加载完成后，但在bean实例化之前，修改BeanDefinition的属性值。调用的是BeanFactoryPostProcessor中的方法
        CustomBeanFactoryPostProcessor beanFactoryPostProcessor = new CustomBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        //获取Bean
        People people = (People) beanFactory.getBean("people");
        System.out.println(people);
    }

    /**
     * 测试BeanPostProcessor类
     * 类的实例化之后修改BeanDefinition类信息
     * @throws Exception
     */
    @Test
    public void testBeanPostProcessor() throws Exception{
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        //ResourceLoader加载资源 -> Resource -> 获取InputStream字节流
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        CustomerBeanPostProcessor beanPostProcessor = new CustomerBeanPostProcessor();
        beanFactory.addBeanPostProcessor(beanPostProcessor);

        People people = (People) beanFactory.getBean("people");
        System.out.println(people);
    }
}
