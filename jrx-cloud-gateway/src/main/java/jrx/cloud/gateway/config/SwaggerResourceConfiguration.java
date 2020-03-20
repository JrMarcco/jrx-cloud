package jrx.cloud.gateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongjc
 * @version 1.0  2020/3/20
 */
@Slf4j
@Primary
@Component
@RequiredArgsConstructor
public class SwaggerResourceConfiguration implements SwaggerResourcesProvider {

    private final RouteLocator routeLocator;
    private final GatewayProperties gatewayProperties;

    @Override
    public List<SwaggerResource> get() {
        var resourceList = new ArrayList<SwaggerResource>();
        var routeList = new ArrayList<String>();

        routeLocator.getRoutes().subscribe(route -> routeList.add(route.getId()));
        gatewayProperties.getRoutes().stream().filter(routeDefinition -> routeList.contains(routeDefinition.getId()))
                .forEach(route -> route.getPredicates().stream()
                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                        .forEach(predicateDefinition -> resourceList.add(swaggerResource(route.getId(),
                                predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
                                        .replace("**", "v2/api-docs"))))

                );
        return resourceList;
    }

    // ----------------------------------------< Private Method >----------------------------------------
    private SwaggerResource swaggerResource(String name, String location) {
        log.info("### Swagger Resource - Name: {}, Location: {} ###", name, location);
        var resource = new SwaggerResource();
        resource.setName(name);
        resource.setLocation(location);
        resource.setSwaggerVersion("1.0");
        return resource;
    }
}
