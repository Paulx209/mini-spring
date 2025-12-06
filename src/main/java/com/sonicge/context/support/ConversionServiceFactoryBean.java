package com.sonicge.context.support;

import com.sonicge.beans.factory.FactoryBean;
import com.sonicge.beans.factory.InitializingBean;
import com.sonicge.core.convert.ConversionService;
import com.sonicge.core.convert.converter.ConvertRegistry;
import com.sonicge.core.convert.converter.Converter;
import com.sonicge.core.convert.converter.ConverterFactory;
import com.sonicge.core.convert.converter.GenericConverter;
import com.sonicge.core.convert.support.DefaultConversionService;
import com.sonicge.core.convert.support.GenericConversionService;

import java.util.Set;

public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {

    private Set<?> converters;
    private GenericConversionService genericConversionService;

    @Override
    public void afterPropertiesSet() throws Exception {
        genericConversionService = new DefaultConversionService();
        registerConverters(converters, genericConversionService);
    }

    private void registerConverters(Set<?> converters, ConvertRegistry convertRegistry) {
        if (converters != null) {
            for (Object converter : converters) {
                if (converter instanceof GenericConverter) {
                    convertRegistry.addGenericConverter((GenericConverter) converter);
                } else if (converter instanceof ConverterFactory<?, ?>) {
                    convertRegistry.addConverterFactory((ConverterFactory<?, ?>) converter);
                } else if (converter instanceof Converter<?, ?>) {
                    convertRegistry.addConverter((Converter<?, ?>) converter);
                } else {
                    throw new IllegalArgumentException("Each converter object must implement one of the " + "Converter, ConverterFactory, or GenericConverter interfaces");
                }
            }
        }
    }


    @Override
    public ConversionService getObject() throws Exception {
        //创建一个ConversionService接口的实现类
        return genericConversionService;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setConverters(Set<?> converters) {
        this.converters = converters;
    }

}
