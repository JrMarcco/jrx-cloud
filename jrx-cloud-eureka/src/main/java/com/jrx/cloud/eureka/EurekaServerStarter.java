package com.jrx.cloud.eureka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author hongjc
 * @version 1.0  2020/3/3
 */
@Slf4j
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerStarter {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerStarter.class, args);

        log.info("### Eureka Server Has Already Started ###");
    }
}
