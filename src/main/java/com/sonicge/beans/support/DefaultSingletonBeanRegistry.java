package com.sonicge.beans.support;

import com.sonicge.beans.BeansException;
import com.sonicge.beans.config.SingletonBeanRegistry;
import com.sonicge.beans.factory.DisposableBean;
import com.sonicge.beans.factory.ObjectFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    //一级缓存
    private Map<String, Object> singletonObjects = new HashMap<>();

    //二级缓存
    protected Map<String, Object> earlySingletonObjects = new HashMap<>();

    //三级缓存
    protected Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    public void registerDisposableBean(String beanName, DisposableBean disposableBean) {
        disposableBeans.put(beanName, disposableBean);
    }

    @Override
    public Object getSingleton(String beanName) {
        //先判断一级缓存中是否存在
        Object singletonObject = singletonObjects.get(beanName);
        if (singletonObject == null) {
            //一级缓存中如果不存在的话，从二级缓存中查找
            singletonObject = earlySingletonObjects.get(beanName);
            if (singletonObject == null) {
                //二级缓存中如果不存在的话，从三级缓存中找 （只针对代理对象）
                ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    singletonObject = singletonFactory.getObject();
                    //将三级缓存中的内容放到二级缓存中
                    earlySingletonObjects.put(beanName, singletonObject);
                    //将三级缓存中beanName删除
                    singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    public void destroySingletons() {
        Set<String> beanNames = disposableBeans.keySet();
        for (String beanName : beanNames) {
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }

    public void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        //添加单例bean的时候，将二级/三级缓存中的内容都去除掉
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    protected void addSingletonFactory(String beanName,ObjectFactory<?> singletonFactory){
        singletonFactories.put(beanName,singletonFactory);
    }
}
