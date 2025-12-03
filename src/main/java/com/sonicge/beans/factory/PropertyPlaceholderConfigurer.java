package com.sonicge.beans.factory;

import cn.hutool.core.bean.BeanException;
import com.sonicge.beans.BeansException;
import com.sonicge.beans.PropertyValue;
import com.sonicge.beans.PropertyValues;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.beans.config.BeanFactoryPostProcessor;
import com.sonicge.core.io.DefaultResourceLoader;
import com.sonicge.core.io.Resource;
import com.sonicge.util.StringValueResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * BeanFactoryProcessor接口的实现类：用来在实例化之前修改BeanDefinition (此时BeanDefinition已经创建好了)
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {
    public static final String PLACEHOLDER_PREFIX = "${";

    public static final String PLACEHOLDER_SUFFIX = "}";

    //存储占位符属性信息的配置文件
    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeanException {
        //加载配置属性文件
        Properties properties = loadProperties();

        //进行占位符替换
        processProperties(beanFactory, properties);

        //在容器中添加字符解析器，供解析@Value注解使用
        StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
        beanFactory.addEmbeddedValueResolver(valueResolver);
    }

    /**
     * 将properties文件中的数据解析成Properties
     *
     * @return
     */
    private Properties loadProperties() {
        try {
            DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
            Resource resource = defaultResourceLoader.getResource(location);
            InputStream inputStream = resource.getInputStream();
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            resolvePropertyValue(beanDefinition, properties);
        }
    }

    private void resolvePropertyValue(BeanDefinition beanDefinition, Properties properties) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            Object value = propertyValue.getValue();
            if (value instanceof String) {
                value = resolvePlaceholder((String) value,properties);
                propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), value));
            }
        }
    }

    private String resolvePlaceholder(String value, Properties properties) {
        //Todo 仅支持一个简单的占位符格式
        String strValue = (String) value;
        StringBuffer buf = new StringBuffer(strValue);
        int startIndex = strValue.indexOf(PLACEHOLDER_PREFIX);
        int endIndex = strValue.indexOf(PLACEHOLDER_SUFFIX);
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            String propKey = strValue.substring(startIndex + 2, endIndex);
            String propValue = properties.getProperty(propKey);
            buf.replace(startIndex, endIndex + 1, propValue);
        }
        return buf.toString();
    }


    public void setLocation(String location) {
        this.location = location;
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        public String resolveStringValue(String strVal) throws BeansException {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(strVal, properties);
        }
    }

}
