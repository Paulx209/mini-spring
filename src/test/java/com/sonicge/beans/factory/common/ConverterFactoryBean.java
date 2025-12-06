package com.sonicge.beans.factory.common;

import com.sonicge.beans.factory.FactoryBean;

import java.util.HashSet;
import java.util.Set;

public class ConverterFactoryBean implements FactoryBean<Set<?>> {
    public Set<?> getObject() throws Exception {
        HashSet<Object> converters = new HashSet<>();
        StringToLocalDateConverter stringToLocalDateConverter = new StringToLocalDateConverter("yyyy - MM - dd");
        converters.add(stringToLocalDateConverter);
        return converters;
    }

    public boolean isSingleton() {
        return true;
    }
}
