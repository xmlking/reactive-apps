package com.example.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurationSupport

@Configuration
class CORSConfiguration: WebFluxConfigurationSupport() {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry//.addMapping("/**")
                .addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
    }
}