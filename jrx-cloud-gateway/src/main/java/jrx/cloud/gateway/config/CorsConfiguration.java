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
    private String allowedHeaders;
    @Value("${cors.allowed_methods:GET, POST, OPTIONS}")
    private String allowedMethods;
    @Value("${cors.allowed_origin:*}")
    private String allowedOrigin;
    @Value("${cors.max_age:3600}")
    private String maxAge;


    @Bean
    public WebFilter corsFilter() {
        return (exchange , chain) -> {
            var request = exchange.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                var response = exchange.getResponse();
                var headers = response.getHeaders();
                headers.add("Access-Control-Allow-Origin", allowedOrigin);
                headers.add("Access-Control-Allow-Methods", allowedMethods);
                headers.add("Access-Control-Allow-Headers", allowedHeaders);
                headers.add("Access-Control-Max-Age", maxAge);
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
