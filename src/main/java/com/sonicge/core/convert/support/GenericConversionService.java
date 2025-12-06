package com.sonicge.core.convert.support;

import cn.hutool.core.convert.BasicType;
import com.sonicge.core.convert.ConversionService;
import com.sonicge.core.convert.converter.ConvertRegistry;
import com.sonicge.core.convert.converter.Converter;
import com.sonicge.core.convert.converter.ConverterFactory;
import com.sonicge.core.convert.converter.GenericConverter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class GenericConversionService implements ConversionService, ConvertRegistry {
    private Map<GenericConverter.ConvertiblePair, GenericConverter> converters = new HashMap<>();

    /**
     * 判断是否存在sourceType类型 到 targetType类型的转换器
     *
     * @param sourceType
     * @param targetType
     * @return
     */
    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        GenericConverter genericConverter = getConverter(sourceType, targetType);
        return genericConverter != null;
    }

    /**
     * 根据sourceType、targetType的继承关系层次，从map中找到对应的converter
     *
     * @param sourceType
     * @param targetType
     * @return
     */
    protected GenericConverter getConverter(Class<?> sourceType, Class<?> targetType) {
        List<Class<?>> sourceTypeHierarchy = getClassHierarchy(sourceType);
        List<Class<?>> targetTypeHierarchy = getClassHierarchy(targetType);

        for (Class<?> sourceClazz : sourceTypeHierarchy) {
            for (Class<?> targetClazz : targetTypeHierarchy) {
                GenericConverter.ConvertiblePair convertiblePair = new GenericConverter.ConvertiblePair(sourceClazz, targetClazz);
                //如果converters中存储了这对类型转换的话，直接返回；那是从哪里存储的呢？
                GenericConverter genericConverter = converters.get(convertiblePair);
                if (genericConverter != null) {
                    return genericConverter;
                }
            }
        }
        return null;
    }

    /**
     * 进行类型转换
     *
     * @param source
     * @param targetType
     * @param <T>
     * @return
     */
    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        Class<?> sourceType = source.getClass();
        targetType = (Class<T>) BasicType.wrap(targetType);
        //应该是遍历自己的层级关系，然后找到对应的Converter！
        GenericConverter converter = getConverter(sourceType, targetType);
        return (T) converter.convert(source, sourceType, targetType);
    }


    // ============================================ConvertRegistry接口============================================

    /**
     * 添加普通的Converter转换器，支持一对一转换
     *
     * @param converter
     */
    @Override
    public void addConverter(Converter<?, ?> converter) {
        //获取converter的泛型信息
        GenericConverter.ConvertiblePair typeInfo = getRequiredTypeInfo(converter);
        ConverterAdapter adapter = new ConverterAdapter(typeInfo, converter);
        for (GenericConverter.ConvertiblePair convertibleType : adapter.getConvertibleTypes()) {
            converters.put(convertibleType, adapter);
        }
    }

    /**
     * 添加高级一点的ConveterFactory转换器，支持一对多转换，就凭他ConvertiblePair中String -> Number。很多类型在进行转换的时候都可以使这个转换器
     *
     * @param converterFactory
     */
    @Override
    public void addConverterFactory(ConverterFactory<?, ?> converterFactory) {
        GenericConverter.ConvertiblePair typeInfo = getRequiredTypeInfo(converterFactory);
        ConverterFactoryAdapter converterFactoryAdapter = new ConverterFactoryAdapter(typeInfo, converterFactory);
        Set<GenericConverter.ConvertiblePair> convertibleTypes = converterFactoryAdapter.getConvertibleTypes();
        for (GenericConverter.ConvertiblePair convertibleType : convertibleTypes) {
            converters.put(convertibleType, converterFactoryAdapter);
        }
    }


    /**
     * 添加更高级的通用转换器，包括属性Set<ConvertiblePair>，只需要实现getConvertibleTypes方法、convert方法即可。
     *
     * @param genericConverter
     */

    @Override
    public void addGenericConverter(GenericConverter genericConverter) {
        for (GenericConverter.ConvertiblePair convertiblePair : genericConverter.getConvertibleTypes()) {
            converters.put(convertiblePair, genericConverter);
        }
    }


    /**
     * 从对象Converter<S,T>中获取到sourceType 和 targetType -> 封装成ConvertiblePair对象
     *
     * @param object
     * @return
     */
    private GenericConverter.ConvertiblePair getRequiredTypeInfo(Object object) {
        Type[] types = object.getClass().getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) types[0];
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Class<?> sourceType = (Class<?>) actualTypeArguments[0];
        Class<?> targetType = (Class<?>) actualTypeArguments[1];
        return new GenericConverter.ConvertiblePair(sourceType, targetType);
    }


    /**
     * 获取类的继承层次关系，为了构造ConvertiblePair
     *
     * @param clazz
     * @return
     */
    private List<Class<?>> getClassHierarchy(Class<?> clazz) {
        List<Class<?>> hierarchy = new ArrayList<>();
        //原始类转为包装类
        clazz = BasicType.wrap(clazz);
        while (clazz != null) {
            hierarchy.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return hierarchy;
    }


    private final class ConverterAdapter implements GenericConverter {
        private final ConvertiblePair typeInfo;

        private final Converter<Object, Object> converter;

        public ConverterAdapter(ConvertiblePair typeInfo, Converter<?, ?> converter) {
            this.typeInfo = typeInfo;
            this.converter = (Converter<Object, Object>) converter;
        }

        /**
         * 返回一个单元素集合
         *
         * @return
         */
        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(typeInfo);
        }

        @Override
        public Object convert(Object source, Class sourceType, Class targetType) {
            return converter.convert(source);
        }
    }

    private final class ConverterFactoryAdapter implements GenericConverter {

        private final ConvertiblePair typeInfo;

        private final ConverterFactory<Object, Object> converterFactory;

        public ConverterFactoryAdapter(ConvertiblePair typeInfo, ConverterFactory<?, ?> converterFactory) {
            this.typeInfo = typeInfo;
            this.converterFactory = (ConverterFactory<Object, Object>) converterFactory;
        }

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(typeInfo);
        }

        @Override
        public Object convert(Object source, Class<?> sourceType, Class<?> targetType) {
            Converter<Object, ?> converter = converterFactory.getConverter(targetType);
            return converter.convert(source);
        }
    }
}
