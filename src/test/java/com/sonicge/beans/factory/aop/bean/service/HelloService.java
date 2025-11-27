package com.sonicge.beans.factory.aop.bean.service;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.context.ApplicationContext;
import com.sonicge.beans.context.ApplicationContextAware;
import com.sonicge.beans.factory.BeanFactory;
import com.sonicge.beans.factory.BeanFactoryAware;

public class HelloService implements ApplicationContextAware, BeanFactoryAware {

    private BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    private String name;

    private Integer age;


    private Boolean isStudent;


    public HelloService() {

    }

    public HelloService(String name, Integer age, Boolean isStudent) {
        this.name = name;
        this.age = age;
        this.isStudent = isStudent;
    }

    public String sayHello() {
        System.out.println("hello");
        return "hello";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getStudent() {
        return isStudent;
    }

    public void setStudent(Boolean student) {
        isStudent = student;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public String toString() {
        return "HelloService{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isStudent=" + isStudent +
                '}';
    }
}
