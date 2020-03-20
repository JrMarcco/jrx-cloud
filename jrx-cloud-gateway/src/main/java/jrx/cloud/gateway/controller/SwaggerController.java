package jrx.cloud.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.*;

import java.util.Optional;

/**
 * @author hongjc
 * @version 1.0  2020/3/20
 */
@RestController
public class SwaggerController {

    @Autowired(required = false)
    private SecurityConfiguration securityConfiguration;
    @Autowired(required = false)
    private UiConfiguration uiConfiguration;

    private final SwaggerResourcesProvider swaggerResourcesProvider;

    @Autowired
    public SwaggerController(SwaggerResourcesProvider swaggerResourcesProvider) {
        this.swaggerResourcesProvider = swaggerResourcesProvider;
    }

    @GetMapping("/swagger-resources/configuration/security")
    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
        return Mono.just(new ResponseEntity<>(
                Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()), HttpStatus.OK));
    }

    @GetMapping("/swagger-resources/configuration/ui")
    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
        return Mono.just(
                new ResponseEntity<>(
                        Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK
                )
        );
    }

    @GetMapping("/swagger-resources")
    public Mono<ResponseEntity<Object>> swaggerResources() {
        return Mono.just((new ResponseEntity<>(swaggerResourcesProvider.get(), HttpStatus.OK)));
    }
}
