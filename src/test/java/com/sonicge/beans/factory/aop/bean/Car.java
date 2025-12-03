package com.sonicge.beans.factory.aop.bean;

import com.sonicge.context.annotation.Value;
import com.sonicge.stereotype.Component;

@Component
public class Car {

    @Value(value = "${brand}")
    private String brand;

    public Car() {

    }

    public Car(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                '}';
    }
}
