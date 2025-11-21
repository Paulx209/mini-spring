package com.sonicge.beans.config;

/**
 * 一个Bean对另一个Bean的引用
 */

public class BeanReference {
    private final String beanName;

    public BeanReference(String beanName){
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
