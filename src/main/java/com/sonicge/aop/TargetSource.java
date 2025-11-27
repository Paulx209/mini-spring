package com.sonicge.aop;

public class TargetSource {
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public Class<?> getTargetClasses() {
        return target.getClass();
    }

    public Object getTarget() {
        return target;
    }
}
