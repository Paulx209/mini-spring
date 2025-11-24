package com.sonicge.core.io;

import java.net.MalformedURLException;
import java.net.URL;

public class DefaultResourceLoader implements ResourceLoader{
    public static final String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 获取对应的资源处理类。
     * @param location
     * @return
     */
    @Override
    public Resource getResource(String location) {
        if(location.startsWith(CLASSPATH_URL_PREFIX)){
            //如果是classpath路径下的资源
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        }
        try {
            //尝试使用URL来处理
            URL url  = new URL(location);
            return new UrlResource(url);
        } catch (MalformedURLException e) {
            //如果不是url，按照文件系统下的资源来处理
//            String path = location;
//            if(location.startsWith("/")){
//                path = location.substring(1);
//            }
            return new FileSystemResource(location);
        }
    }
}
