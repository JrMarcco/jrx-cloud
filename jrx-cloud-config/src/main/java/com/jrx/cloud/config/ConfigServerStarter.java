package com.jrx.cloud.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author hongjc
 * @version 1.0  2020/3/3
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer
public class ConfigServerStarter {

    private static final Logger log = LoggerFactory.getLogger(ConfigServerStarter.class);

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerStarter.class, args);

        log.info("### Config Server Has Already Started ###");
    }
}
