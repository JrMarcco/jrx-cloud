package com.jrx.cloud.netty.server;

import com.jrx.cloud.netty.common.messgae.MessagePackage;
import com.jrx.cloud.netty.server.config.ConfigPackage;
import com.jrx.cloud.netty.server.handler.HandlerPackage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author x
 * @version 1.0  2021/6/29
 */
@Slf4j
@SpringBootApplication
@ComponentScan(basePackageClasses = {MessagePackage.class, ConfigPackage.class, HandlerPackage.class})
public class NettyServerStarter {

    public static void main(String[] args) {
        SpringApplication.run(NettyServerStarter.class, args);

        log.info("### Netty Server Has Already Started ###");
    }
}
