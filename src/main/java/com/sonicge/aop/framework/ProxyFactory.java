package com.sonicge.aop.framework;

import com.sonicge.aop.AdvicedSupport;

public class ProxyFactory {
    private AdvicedSupport advicedSupport;

    public ProxyFactory(AdvicedSupport advicedSupport){
        this.advicedSupport = advicedSupport;
    }

    public Object getProxy(){
        return createAopProxy().getProxy();
    }

    public AopProxy createAopProxy(){
        if(advicedSupport.isProxyTargetClass()){
            //为True -> cglib动态代理
            return new CglibAopProxy(advicedSupport);
        }else{
            return new JdkDynamicAopProxy(advicedSupport);
        }
    }
}
