package com.sonicge.beans;

public class PropertyValue {
    //属性名称
    private String name;

    //属性内容
    private Object value;

    public PropertyValue() {

    }

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
