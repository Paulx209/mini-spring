package com.sonicge.beans.factory.ioc.bean.service;

public class HelloService {
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
    public String toString() {
        return "HelloService{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isStudent=" + isStudent +
                '}';
    }
}
