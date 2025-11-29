package com.sonicge.aop;

public class TargetSource {
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public Class<?>[] getTargetClasses() {
        return target.getClass().getInterfaces();
    }

    public ClassLoader getClassLoader(){
        return target.getClass().getClassLoader();
    }



    public Object getTarget() {
        return target;
    }
}
