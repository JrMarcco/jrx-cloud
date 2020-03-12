package com.jrx.cloud.eureka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author hongjc
 * @version 1.0  2020/3/3
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerStarter {

    private static final Logger log = LoggerFactory.getLogger(EurekaServerStarter.class);

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerStarter.class, args);

        log.info("### Eureka Server Has Already Started ###");
    }
}
