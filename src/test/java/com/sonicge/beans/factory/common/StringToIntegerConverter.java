package com.sonicge.beans.factory.common;

import com.sonicge.core.convert.converter.Converter;
import com.sonicge.core.convert.converter.GenericConverter;

import java.util.Collections;
import java.util.Set;

public class StringToIntegerConverter implements Converter<String,Integer> {

    @Override
    public Integer convert(String data) {
        return Integer.valueOf(data);
    }
}
