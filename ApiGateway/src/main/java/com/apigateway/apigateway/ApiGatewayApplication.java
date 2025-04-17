package com.apigateway.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
    @Bean
    public RouteLocator gatewayRoot(RouteLocatorBuilder builder)
    {
        return builder.routes()
                .route("user",r -> r.path("/user/**").uri("lb://user"))
                .route("spa",r -> r.path("/spa/**").uri("lb://spa"))
                .route("complaint",r -> r.path("/complaint/**").uri("lb://complaint"))
                .route("restaurants",r -> r.path("/restaurants/**").uri("lb://Restaurants"))
                .route("blocs",r -> r.path("/blocs/**").uri("lb://blocs"))
                .route("chambre",r -> r.path("/chambre/**").uri("lb://chambre"))
                .route("activity",r -> r.path("/activity/**").uri("lb://activity"))
                .build();
    }

}
