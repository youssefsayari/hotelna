package com.apigateway.apigateway.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsGlobalConfiguration {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("http://localhost:4200"); // frontend Angular
        corsConfig.addAllowedMethod("*"); // GET, POST, etc.
        corsConfig.addAllowedHeader("*"); // Authorization, Content-Type, etc.
        corsConfig.setAllowCredentials(true); // si tu utilises les cookies / headers d'auth

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig); // toutes les routes

        return new CorsWebFilter(source);
    }
}
