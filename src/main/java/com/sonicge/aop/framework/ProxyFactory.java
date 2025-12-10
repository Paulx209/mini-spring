package com.sonicge.aop.framework;

import com.sonicge.aop.AdvicedSupport;

public class ProxyFactory  extends AdvicedSupport{
    public ProxyFactory(){
    }

    /**
     * 创建代理对象的方法
     *
     */
    public Object getProxy(){
        return createAopProxy().getProxy();
    }

    /**
     * 创建代理对象的核心方法，创建哪一类型的代理对象
     *
     */
    public AopProxy createAopProxy(){
        if(this.isProxyTargetClass() || this.getTargetSource().getTargetClasses().length == 0){
            return new CglibAopProxy(this);
        }
        return new JdkDynamicAopProxy(this);
    }
}
