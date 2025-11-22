package com.sonicge.beans;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {
    private List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue propertyValue) {
        //增加属性值替换功能。
        for (int i = 0; i < propertyValueList.size(); i++) {
            PropertyValue currentPropertyValue = propertyValueList.get(i);
            if(currentPropertyValue.getName().equals(propertyValue.getName())){
                //如果属性名相同的话，直接进行覆盖！
                this.propertyValueList.set(i,propertyValue);
                return;
            }
        }
        propertyValueList.add(propertyValue);
    }

    /**
     * 将集合转换为数组
     *
     * @return
     */
    public PropertyValue[] getPropertyValues() {
        return propertyValueList.toArray(new PropertyValue[0]);
    }

    /**
     * 根据propertyName获取propertyValue
     *
     * @param propertyName
     * @return
     */
    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : propertyValueList) {
            if (propertyValue.getName().equals(propertyName)) {
                return propertyValue;
            }
        }
        return null;
    }

}
