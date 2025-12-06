package com.sonicge.core.convert;

public interface ConversionService  {
    /**
     * 要转换的话，就要有对应的converte
     * @param sourceType
     * @param targetType
     * @return
     */
    boolean canConvert(Class<?> sourceType,Class<?> targetType);

    <T> T convert(Object source,Class<T> targetType);
}
