package jrx.cloud.gateway;

import com.jrx.cloud.common.config.global.GlobalExceptionHandler;
import com.jrx.cloud.common.config.springboot.JacksonConfig;
import jrx.cloud.gateway.config.ConfigPackage;
import jrx.cloud.gateway.controller.ControllerPackage;
import jrx.cloud.gateway.filter.FilterPackage;
import jrx.cloud.gateway.remote.RemotePackage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hongjc
 * @version 1.0  2020/3/20
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {RemotePackage.class})
@ComponentScan(basePackageClasses = {GlobalExceptionHandler.class, JacksonConfig.class, ConfigPackage.class, FilterPackage.class, ControllerPackage.class})
public class ApiGatewayStarter {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayStarter.class, args);

        log.info("### Api Gateway Has Already Started ###");
    }
}
