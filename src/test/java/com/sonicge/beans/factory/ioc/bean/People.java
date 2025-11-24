package com.sonicge.beans.factory.ioc.bean;

import com.sonicge.beans.config.BeanReference;
import com.sonicge.beans.factory.DisposableBean;
import com.sonicge.beans.factory.InitializingBean;

public class People implements InitializingBean, DisposableBean {
    private String name;

    private Integer age;

    private Boolean isStudent;

    private Car car;

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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }



    public void customDestroyMethod() {
        System.out.println("I died in the method named customDestroyMethod");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("I was born in the method named afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("I died in the method named destroy");
    }

    public void customInitMethod(){
        System.out.println("I was born in the method named customInitMethod");
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isStudent=" + isStudent +
                ", car=" + car +
                '}';
    }
}
