package com.sonicge.beans;

public class PropertyValue {
    //属性名称
    private String name;

    //属性内容
    private String value;

    public PropertyValue() {

    }

    public PropertyValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
