package com.sonicge.beans.factory.service;

import com.sonicge.beans.BeansException;

public class WorldServiceImpl implements WorldService {
    @Override
    public void explode() {
        System.out.println("The earth is beautiful");
        throw new BeansException("故意抛出的异常");
    }
}
