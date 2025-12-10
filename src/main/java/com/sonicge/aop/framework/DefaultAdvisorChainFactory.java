package com.sonicge.aop.framework;

import com.sonicge.aop.*;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DefaultAdvisorChainFactory implements  AdvisorChainFactory{
    /**
     * 获取拦截器以及动态拦截增强
     * @param config
     * @param method
     * @param targetClass
     * @return
     */
    @Override
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(AdvicedSupport config, Method method, Class<?> targetClass) {
        Advisor[] advisors = config.getAdvisors().toArray(new Advisor[0]);
        ArrayList<Object> interceptorList = new ArrayList<>(advisors.length);
        Class<?> actualClass = targetClass != null ? targetClass : method.getDeclaringClass();
        for(Advisor advisor : advisors){
            PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
            PointCut pointcut = pointcutAdvisor.getPointcut();
            ClassFilter classFilter = pointcut.getClassFilter();
            MethodMatcher methodMatcher = pointcut.getMethodMatcher();
            if(classFilter.matches(actualClass) && methodMatcher.matches(method,actualClass)){
                //如果class和method都符合的话，才可以将advice增强添加到对应的拦截器链中
                MethodInterceptor advice = (MethodInterceptor) pointcutAdvisor.getAdvice();
                interceptorList.add(advice);
            }
        }
        return interceptorList;
    }
}
