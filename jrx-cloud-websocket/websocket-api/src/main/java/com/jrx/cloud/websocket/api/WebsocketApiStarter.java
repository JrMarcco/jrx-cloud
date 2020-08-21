package com.jrx.cloud.websocket.api;

import com.jrx.cloud.common.config.context.ApplicationContextListener;
import com.jrx.cloud.common.util.ApplicationContextUtils;
import com.jrx.cloud.websocket.api.config.ConfigPackage;
import com.jrx.cloud.websocket.api.config.WebSocketConfig;
import com.jrx.cloud.websocket.api.handler.HandlerPackage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hongjc
 * @version 1.0  2020/8/10
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackageClasses = {ConfigPackage.class, ApplicationContextListener.class, HandlerPackage.class})
public class WebsocketApiStarter {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketApiStarter.class, args);
        log.info("### Websocket Server Has Already Started ###");

        ApplicationContextUtils.getBean(WebSocketConfig.class).start();
    }
}
