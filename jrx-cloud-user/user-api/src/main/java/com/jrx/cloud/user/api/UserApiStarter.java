package com.jrx.cloud.user.api;

import com.jrx.cloud.common.config.springboot.JacksonConfig;
import com.jrx.cloud.user.api.config.ConfigPackage;
import com.jrx.cloud.user.api.controller.ControllerPackage;
import com.jrx.cloud.user.api.mapper.MapperPackage;
import com.jrx.cloud.user.api.service.impl.ServicePackage;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hongjc
 * @version 1.0  2020/3/12
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackageClasses = {JacksonConfig.class, ConfigPackage.class, ControllerPackage.class, ServicePackage.class})
@MapperScan(basePackageClasses = {MapperPackage.class})
public class UserApiStarter {

    public static void main(String[] args) {
        SpringApplication.run(UserApiStarter.class, args);
        log.info("### User Server Has Already Started ###");
    }
}
