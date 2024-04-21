package com.medicarepro.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    private final RouteConfigProperties routeConfigProperties;

    @Autowired
    public GatewayConfig(RouteConfigProperties routeConfigProperties) {
        this.routeConfigProperties = routeConfigProperties;
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        Builder routes = builder.routes();
        System.out.println("ROUTES "+routeConfigProperties.getRoutes());
        routeConfigProperties.getRoutes().forEach((key, value) ->
                routes
                        .route(key, route -> route.path("/api/" + extractServiceName(key) + "/**").uri(value))
                        .route(key, route -> route.path("/api/" + extractServiceName(key)).uri(value))
        );

        return routes.build();
    }

    private String extractServiceName(String url) {
        return url.contains("-") ? url.split("-")[0] : "";
    }
}
