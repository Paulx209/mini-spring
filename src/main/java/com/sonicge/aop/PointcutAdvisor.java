package com.sonicge.aop;

public interface PointcutAdvisor extends Advisor {

    PointCut getPointcut();
}
