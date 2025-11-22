package com.sonicge.beans.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.sonicge.beans.BeansException;
import com.sonicge.beans.PropertyValue;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.beans.config.BeanReference;
import com.sonicge.beans.support.AbstractBeanDefinitionReader;
import com.sonicge.beans.support.BeanDefinitionRegistry;
import com.sonicge.core.io.Resource;
import com.sonicge.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public static final String BEAN_ELEMENT = "bean";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String ID_ELEMENT = "id";
    public static final String NAME_ELEMENT = "name";
    public static final String CLASS_ELEMENT = "class";
    public static final String VALUE_ELEMENT = "value";
    public static final String REF_ELEMENT = "ref";

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            InputStream inputStream = resource.getInputStream();
            try {
                doLoadBeanDefinitions(inputStream);
            } finally {
                inputStream.close();
            }
        } catch (IOException ex) {
            throw new BeansException("IOException parsing XML document from " + resource, ex);
        }
    }


    private void doLoadBeanDefinitions(InputStream inputStream) {
        Document document = XmlUtil.readXML(inputStream);
        //<beans>标签
        Element root = document.getDocumentElement();
        //<bean>标签
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i) instanceof Element) {
                System.out.println("标签的名称为：" + childNodes.item(i).getNodeName());
                if (BEAN_ELEMENT.equals(childNodes.item(i).getNodeName())) {
                    //解析Bean标签
                    try {
                        Element bean = (Element) childNodes.item(i);
                        String id = bean.getAttribute(ID_ELEMENT);
                        String name = bean.getAttribute(NAME_ELEMENT);
                        String className = bean.getAttribute(CLASS_ELEMENT);

                        //BeanDefinition的第一个参数 ---clazz对象
                        Class<?> clazz = Class.forName(className);
                        //id的优先级是高于name的
                        String beanName = StrUtil.isNotEmpty(id) ? id : name;
                        if (StrUtil.isEmpty(beanName)) {
                            //如果id和name都为空的话，那么就默认使用类名小写作为beanName
                            beanName = StrUtil.lowerFirst(clazz.getSimpleName());
                        }
                        //创建BeanDefinition对象
                        BeanDefinition beanDefinition = new BeanDefinition(clazz);

                        //开始遍历bean标签中的子标签
                        NodeList beanChildNodes = bean.getChildNodes();
                        for (int j = 0; j < beanChildNodes.getLength(); j++) {
                            if (PROPERTY_ELEMENT.equals(beanChildNodes.item(i).getNodeName())) {
                                //开始解析property标签
                                Element property = (Element) beanChildNodes.item(i);
                                String propertyName = property.getAttribute(NAME_ELEMENT);
                                String propertyValue = property.getAttribute(VALUE_ELEMENT);
                                String propertyRef = property.getAttribute(REF_ELEMENT);

                                Object value = propertyValue;
                                if (StrUtil.isNotBlank(propertyRef)) {
                                    value = new BeanReference(propertyRef);
                                }
                                PropertyValue myProperty = new PropertyValue(propertyName, value);
                                beanDefinition.getPropertyValues().addPropertyValue(myProperty);
                            }
                        }
                        //将beanDefinition保存到注册表中
                        getRegistry().registerBeanDefinition(beanName, beanDefinition);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
