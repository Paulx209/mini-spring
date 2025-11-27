package com.sonicge.beans.factory.aop.bean;

import com.sonicge.beans.factory.FactoryBean;

public class CarFactoryBean implements FactoryBean<Car> {
    @Override
    public Car getObject() throws Exception {
        Car car = new Car();
        car.setBrand("Benz");
        return car;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
