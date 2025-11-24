package com.sonicge.beans.support;

import com.sonicge.beans.xml.XmlBeanDefinitionReader;

public abstract  class AbstractXmlApplicationContext extends AbstractRefreshApplicationContext {
    @Override
    protected void loadBeanDefinition(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String[] configLocations = getConfigLocations();
        if(configLocations != null){
            xmlBeanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}
