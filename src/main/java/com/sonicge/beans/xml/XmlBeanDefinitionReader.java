package com.sonicge.beans.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.sonicge.beans.BeansException;
import com.sonicge.beans.PropertyValue;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.beans.config.BeanReference;
import com.sonicge.beans.support.AbstracteBeanDefinitionReader;
import com.sonicge.beans.support.BeanDefinitionRegistry;
import com.sonicge.core.io.Resource;
import com.sonicge.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstracteBeanDefinitionReader {
    public static final String BEAN_ELEMENT = "bean";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";

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
                doLoadBeanDefinition(inputStream);
            } finally {
                inputStream.close();
            }
        } catch (Exception e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    protected void doLoadBeanDefinition(InputStream inputStream) throws Exception {
        Document document = XmlUtil.readXML(inputStream);
        //beans标签
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        //遍历所有的bean标签
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i) instanceof Element) {
                String nodeName = childNodes.item(i).getNodeName();
                if (BEAN_ELEMENT.equals(nodeName)) {
                    //解析Bean标签
                    Element beanElement = (Element) childNodes.item(i);
                    String id = beanElement.getAttribute(ID_ATTRIBUTE);
                    String name = beanElement.getAttribute(NAME_ATTRIBUTE);
                    String className = beanElement.getAttribute(CLASS_ATTRIBUTE);

                    Class<?> clazz = Class.forName(className);
                    //id的优先级大于name
                    String beanName = StrUtil.isNotEmpty(id) ? id : name;
                    if (StrUtil.isEmpty(beanName)) {
                        //如果id、name都为空
                        beanName = StrUtil.lowerFirst(clazz.getSimpleName());
                    }

                    BeanDefinition beanDefinition = new BeanDefinition(clazz);

                    //开始解析property标签
                    for (int j = 0; j < beanElement.getChildNodes().getLength(); j++) {
                        if (beanElement.getChildNodes().item(j) instanceof Element) {
                            Element property = (Element) beanElement.getChildNodes().item(j);
                            if (PROPERTY_ELEMENT.equals(property.getNodeName())) {
                                String propertyName = property.getAttribute(NAME_ATTRIBUTE);
                                String propertyValue = property.getAttribute(VALUE_ATTRIBUTE);
                                String propertyRef = property.getAttribute(REF_ATTRIBUTE);
                                Object value = propertyValue;
                                if (StrUtil.isNotEmpty(propertyRef)) {
                                    //这里的ref的值 -> <bean>标签中的id属性； 添加BeanDefinition的时候，id属性优先级也是最高的。
                                    value = new BeanReference(propertyRef);
                                }
                                PropertyValue pv = new PropertyValue(propertyName, value);
                                beanDefinition.getPropertyValues().addPropertyValue(pv);
                            }
                        }
                    }
                    getRegistry().registerBeanDefinition(beanName, beanDefinition);
                }
            }
        }
    }

}
