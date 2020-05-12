package jrx.cloud.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

/**
 * @author hongjc
 * @version 1.0  2019/1/15
 */
@Configuration
public class CorsConfiguration {

    @Value("${cors.allowed_headers:x-requested-with, authorization,  Authorization, Content-Type, credential, X-XSRF-TOKEN}")
    private String allowed_headers;
    @Value("${cors.allowed_methods:GET, POST, OPTIONS}")
    private String allowed_methods;
    @Value("${cors.allowed_origin:*}")
    private String allowed_origin;
    @Value("${cors.max_age:3600}")
    private String max_age;


    @Bean
    public WebFilter corsFilter() {
        return (exchange , chain) -> {
            var request = exchange.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                var response = exchange.getResponse();
                var headers = response.getHeaders();
                headers.add("Access-Control-Allow-Origin", allowed_origin);
                headers.add("Access-Control-Allow-Methods", allowed_methods);
                headers.add("Access-Control-Allow-Headers", allowed_headers);
                headers.add("Access-Control-Max-Age", max_age);
                headers.add("Vary", "Origin");
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(exchange);
        };
    }
}
