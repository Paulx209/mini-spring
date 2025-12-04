package com.sonicge.core.convert.converter;

public interface Converter<S,T> {
    T convert(S data);
}
