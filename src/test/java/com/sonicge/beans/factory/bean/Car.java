package com.sonicge.beans.factory.bean;

import com.sonicge.context.annotation.Value;
import com.sonicge.stereotype.Component;

import java.time.LocalDate;

@Component
public class Car {
    private int price;

    private LocalDate produceDate;

    @Value(value = "${brand}")
    private String brand;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(LocalDate produceDate) {
        this.produceDate = produceDate;
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
                "price=" + price +
                ", produceDate=" + produceDate +
                ", brand='" + brand + '\'' +
                '}';
    }
}
