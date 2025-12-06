package com.sonicge.core.convert.support;

import com.sonicge.core.convert.converter.Converter;
import com.sonicge.core.convert.converter.ConverterFactory;

public class StringToNumberConverter implements ConverterFactory<String, Number> {
    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToNumber<T>(targetType);
    }

    /**
     * 创建一个字符串转换为数字类型的转换器
     *
     * @param <T>
     */
    private static final class StringToNumber<T extends Number> implements Converter<String, T> {
        private Class<T> targetType;

        public StringToNumber(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(String data) {
            if (data.length() == 0) {
                return null;
            }
            if (targetType.equals(Integer.class)) {
                return (T) Integer.valueOf(data);
            }else if(targetType.equals(Long.class)){
                return (T) Long.valueOf(data);
            }else if (targetType.equals(Double.class)){
                return (T) Double.valueOf(data);
            }// todo 其他类型
            else{
                throw new IllegalArgumentException(
                        "Cannot convert String [" + data + "] to target class [" + targetType.getName() + "]");
            }
        }
    }
}
