package com.sonicge.beans.support;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.config.BeanDefinition;
import com.sonicge.beans.factory.ConfigurableListableBeanFactory;

import java.util.*;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition != null) {
            return beanDefinition;
        } else {
            throw new BeansException("没有找到对应的Bean:" + beanName);
        }
    }

    /**
     * 判断 是否存在beanName对应的BeanDefinition
     *
     * @param beanName
     * @return
     */

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }


    /**
     * 获取type类型的Bean
     *
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> result = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                T bean = (T) getBean(beanName);
                result.put(beanName, bean);
            }
        });
        return result;
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        List<String> beanNames = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            BeanDefinition beanDefinition = entry.getValue();
            Class beanClass = beanDefinition.getBeanClass();
            if (requiredType.isAssignableFrom(beanClass)) {
                //如果类型符合的话
                beanNames.add(entry.getKey());
            }
        }
        if (beanNames.size() == 1) {
            return getBean(beanNames.get(0), requiredType);
        }

        throw new BeansException(requiredType + "expected single bean but found " +
                beanNames.size() + ": " + beanNames);
    }


    @Override
    public String[] getBeanDefinitionNames() {
        Set<String> beanNameSet = beanDefinitionMap.keySet();
        return beanNameSet.toArray(new String[beanNameSet.size()]);
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        //name  beanDefinition
        beanDefinitionMap.entrySet().stream().forEach(entry -> {
            if (entry.getValue().isSingleton() && !entry.getValue().isLazy()) {
                getBean(entry.getKey());
            }
        });
    }
}
