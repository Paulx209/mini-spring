package com.sonicge.beans.support;

import com.sonicge.beans.BeansException;
import com.sun.tools.javac.code.Attribute;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private String[] configLocations;

    public ClassPathXmlApplicationContext(String configLocations) {
        this(new String[]{configLocations});
    }

    /**
     * 从xml文件加载BeanDefinition，并且自动刷新上下文
     *
     * @param configLocations xml配置文件
     * @throws BeansException 应用上下文创建失败
     */
    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        this.configLocations = configLocations;
        refresh();
    }


    @Override
    protected String[] getConfigLocations() {
        return this.configLocations;
    }
}
