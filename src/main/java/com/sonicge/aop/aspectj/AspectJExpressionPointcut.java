package com.sonicge.aop.aspectj;

import com.sonicge.aop.ClassFilter;
import com.sonicge.aop.MethodMatcher;
import com.sonicge.aop.PointCut;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;


public class AspectJExpressionPointcut implements PointCut, ClassFilter, MethodMatcher {

    //1.支持切点表达式的语法，是一个集合。
    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<PointcutPrimitive>();

    static {
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    private final PointcutExpression pointcutExpression;

    public AspectJExpressionPointcut(String expression) {
        //调用PointcutParser的静态构造方法创建一个切点的解析器，传入两个参数，第一个参数是支持的切点表达式的语法，是一个集合；第二个参数是类加载器
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());
        //然后用解析器对入参expression进行解析，并且将结果赋值给属性pointcutExpression
        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    @Override
    public boolean matches(Class<?> clazz) {
        //pointcutExpression内部可以实现是否和方法match
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        //pointcutExpression内部可以实现是否和具体的类match
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
