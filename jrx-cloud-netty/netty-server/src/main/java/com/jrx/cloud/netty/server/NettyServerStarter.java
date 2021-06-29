package com.jrx.cloud.netty.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author x
 * @version 1.0  2021/6/29
 */
@Slf4j
@SpringBootApplication
public class NettyServerStarter {

    public static void main(String[] args) {
        SpringApplication.run(NettyServerStarter.class, args);

        log.info("### Netty Server Has Already Started ###");
    }
}
