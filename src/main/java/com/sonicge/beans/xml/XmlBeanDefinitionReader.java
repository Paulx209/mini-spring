package com.sonicge.beans.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.sonicge.beans.BeansException;
import com.sonicge.beans.PropertyValue;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.beans.config.BeanReference;
import com.sonicge.beans.support.AbstractBeanDefinitionReader;
import com.sonicge.beans.support.BeanDefinitionRegistry;
import com.sonicge.context.annotation.ClassPathBeanDefinitionScanner;
import com.sonicge.core.io.Resource;
import com.sonicge.core.io.ResourceLoader;
import com.sonicge.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public static final String BEAN_ATTRIBUTE = "bean";
    public static final String PROPERTY_ATTRIBUTE = "property";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";
    public static final String SCOPE_ATTRIBUTE = "scope";

    public static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
    public static final String COMPONENT_SCAN_ATTRIBUTE = "context:component-scan";

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
                String nodeName = childNodes.item(i).getNodeName();
                System.out.println("标签的名称为：" + nodeName);
                if (COMPONENT_SCAN_ATTRIBUTE.equals(nodeName)) {
                    Element componentScan = (Element) childNodes.item(i);
                    String basePackage = componentScan.getAttribute(BASE_PACKAGE_ATTRIBUTE);
                    if (StrUtil.isEmpty(basePackage)) {
                        throw new BeansException("The value of base-package attribute can not be empty or null");
                    }
                    scanPackage(basePackage);
                } else if (BEAN_ATTRIBUTE.equals(nodeName)) {
                    //解析Bean标签
                    try {
                        Element bean = (Element) childNodes.item(i);
                        String id = bean.getAttribute(ID_ATTRIBUTE);
                        String name = bean.getAttribute(NAME_ATTRIBUTE);
                        String className = bean.getAttribute(CLASS_ATTRIBUTE);
                        String initMethodName = bean.getAttribute(INIT_METHOD_ATTRIBUTE);
                        String destroyMethodName = bean.getAttribute(DESTROY_METHOD_ATTRIBUTE);
                        String scope = bean.getAttribute(SCOPE_ATTRIBUTE);

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
                        beanDefinition.setInitMethodName(initMethodName);
                        beanDefinition.setDestroyMethodName(destroyMethodName);

                        if (StrUtil.isNotEmpty(scope)) {
                            beanDefinition.setScope(scope);
                        }
                        if (scope.equals(BeanDefinition.SCOPE_PROTOTYPE)) {
                            //如果是多实例的话
                            beanDefinition.setSingleton(false);
                            beanDefinition.setPrototype(true);
                        }

                        //开始遍历bean标签中的子标签
                        NodeList beanChildNodes = bean.getChildNodes();
                        for (int j = 0; j < beanChildNodes.getLength(); j++) {
                            if (PROPERTY_ATTRIBUTE.equals(beanChildNodes.item(j).getNodeName())) {
                                //开始解析property标签
                                Element property = (Element) beanChildNodes.item(j);
                                String propertyName = property.getAttribute(NAME_ATTRIBUTE);
                                String propertyValue = property.getAttribute(VALUE_ATTRIBUTE);
                                String propertyRef = property.getAttribute(REF_ATTRIBUTE);

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

    private void scanPackage(String basePackage){
        String[] basePackages = StrUtil.splitToArray(basePackage, ',');
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.scan(basePackages);
    }

}
