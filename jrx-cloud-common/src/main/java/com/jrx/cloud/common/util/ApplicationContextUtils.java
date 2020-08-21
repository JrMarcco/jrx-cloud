package com.jrx.cloud.common.util;

import org.springframework.context.ApplicationContext;

/**
 * @author hongjc
 * @version 1.0  2020/8/21
 */
public class ApplicationContextUtils {

    private static ApplicationContext context;

    public static void setContext(ApplicationContext applicationContext) {
        if (context == null && applicationContext != null) {
            context = applicationContext;
        }
    }

    public static <T> T getBean(Class<T> t) {
        return context.getBean(t);
    }
}
