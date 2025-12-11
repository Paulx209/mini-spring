package com.sonicge.aop;


import com.sonicge.aop.framework.AdvisorChainFactory;
import com.sonicge.aop.framework.DefaultAdvisorChainFactory;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AdvicedSupport {
    //是否使用cglib代理,默认使用jdk动态代理; false -> jdk代理 ; true -> cglib代理
    private boolean proxyTargetClass = true;

    private TargetSource targetSource;

    private MethodInterceptor methodInterceptor;

    private MethodMatcher methodMatcher;

    //缓存method对应的拦截器链
    private transient Map<Integer, List<Object>> methodCache;

    private List<Advisor> advisors = new ArrayList<>();

    AdvisorChainFactory advisorChainFactory = new DefaultAdvisorChainFactory();

    public AdvicedSupport(){
        //methodCache为线程安全的hash数组
        this.methodCache = new ConcurrentHashMap<>(32);
    }

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public void addAdvisor(Advisor advisor){
        advisors.add(advisor);
    }

    public List<Advisor> getAdvisors(){
        return advisors;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    /**
     * 用来返回方法的拦截器链
     * 会先从methodCache中获取
     * @param method
     * @param targetClass
     * @return
     */
    public List<Object> getInterceptorAndDynamicInterceptionAdvice(Method method, Class<?> targetClass){
        //获取method的hash值，以hash值为key查找对应的拦截器链
        Integer cacheKey = method.hashCode();
        List<Object> cached = this.methodCache.get(cacheKey);
        //如果该方法的拦截器链（advice）为null的话
        if(cached == null){
            //获取该方法的拦截器链
            cached = this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(this,method,targetClass);
            this.methodCache.put(cacheKey,cached);
        }
        return cached;
    }
}
