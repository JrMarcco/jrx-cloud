package com.jrx.cloud.common.config.context;

import com.jrx.cloud.common.util.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author hongjc
 * @version 1.0  2020/8/21
 */
@Slf4j
@Component
public class ApplicationContextListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContextUtils.setContext(contextRefreshedEvent.getApplicationContext());
    }
}
