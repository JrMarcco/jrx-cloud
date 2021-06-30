package com.jrx.cloud.netty.client;

import com.jrx.cloud.netty.client.config.ConfigPackage;
import com.jrx.cloud.netty.client.handler.HandlerPackage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author x
 * @version 1.0  2021/6/30
 */
@Slf4j
@SpringBootApplication
@ComponentScan(basePackageClasses = {ConfigPackage.class, HandlerPackage.class})
public class NettyClientStarter {

    public static void main(String[] args) {
        SpringApplication.run(NettyClientStarter.class, args);

        log.info("### Netty Client Has Already Started ###");
    }

}
