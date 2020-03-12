package com.jrx.cloud.user.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author hongjc
 * @version 1.0  2020/3/12
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    private static final String BASE_PACKAGES = "com.jrx.cloud.user.api";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGES))
                .paths(PathSelectors.any())
                .build();
    }

    // ----------------------------------------< Private Method >----------------------------------------
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Jrx Cloud User Center Restful Apis")
                .description("用户中心Api文档")
                .version("1.0-SNAPSHOT")
                .build();
    }
}
