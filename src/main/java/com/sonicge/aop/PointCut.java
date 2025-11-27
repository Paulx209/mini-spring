package com.sonicge.aop;

public interface PointCut{
    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
