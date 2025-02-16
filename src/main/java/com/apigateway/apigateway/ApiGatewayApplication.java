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
                .route("candidats",r -> r.path("/candidats/**").uri("lb://CandidatSAE6"))
                .route("jobs",r -> r.path("/jobs/**").uri("lb://JobSAE6"))
                .build();
    }

}
