package com.sonicge.beans.factory.ioc;

import com.sonicge.beans.factory.common.StringToBooleanConverter;
import com.sonicge.beans.factory.common.StringToIntegerConverter;
import com.sonicge.core.convert.converter.Converter;
import com.sonicge.core.convert.support.GenericConversionService;
import com.sonicge.core.convert.support.StringToNumberConverter;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TypeConversionFirstPartTest {
    /**
     * 直接实现Converter接口的convert()方法！level 1
     */
    @Test
    public void testStringToIntegerConverter(){
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer convert = converter.convert("129412");
        System.out.println(convert);
    }

    /**
     * 实现ConverterFactory接口的getConverter()方法，获取到转换器之后，在调用Convert()方法  level 2
     */
    @Test
    public void testStringToNumberConverter(){
        StringToNumberConverter  converterFactory= new StringToNumberConverter();
        Converter<String, Long> converter = converterFactory.getConverter(Long.class);
        Long convert = converter.convert("129412");
        System.out.println(convert);

        Converter<String, Integer> converter1 = converterFactory.getConverter(Integer.class);
        Integer convert1 = converter1.convert("23132");
        System.out.println(convert1);
    }
    @Test
    public void testGenericConverter(){
        StringToBooleanConverter converter  = new StringToBooleanConverter();

        Boolean convert = (Boolean)converter.convert("1", String.class, Boolean.class);
        System.out.println(convert);
    }

    @Test
    public void testGenericConversionService(){
        GenericConversionService conversionService = new GenericConversionService();
        //添加Converter
        conversionService.addConverter(new StringToIntegerConverter());

        //调用convert方法 -> 会获取到source的Type类型，以及Integer的Type类型，从convertMap中寻找对应的！
        Integer intNum = conversionService.convert("888",Integer.class);
        System.out.println(intNum);

        conversionService.addConverterFactory(new StringToNumberConverter());
        //可以转换的 因为添加了一个String->Number的Converter转换器； String遍历的时候会碰到Long类型
        assertThat(conversionService.canConvert(String.class, Long.class)).isTrue();
        Long convert = conversionService.convert("1111", Long.class);
        System.out.println(convert);

        conversionService.addGenericConverter(new StringToBooleanConverter());
        conversionService.canConvert(String.class,Boolean.class);
        Boolean aTrue = conversionService.convert("true", Boolean.class);
        System.out.println(aTrue);
    }

}
