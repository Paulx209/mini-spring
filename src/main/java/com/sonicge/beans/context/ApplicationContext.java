package com.sonicge.beans.context;

import com.sonicge.beans.factory.HierarchicalBeanFactory;
import com.sonicge.beans.factory.ListableBeanFactory;
import com.sonicge.core.io.ResourceLoader;

/**
 * 应用上下文
 * 是Spring中比BeanFactory中更先进的IOC容器，ApplicationContext除了BeanFactory中的所有功能之外，还支持BeanFactoryPostProcessor 、 BeanPostProcessor的自动识别、资源加载、容器事件和监听器、国际化支持、单例bean自动初始化等。
 *
 */

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader {
}
