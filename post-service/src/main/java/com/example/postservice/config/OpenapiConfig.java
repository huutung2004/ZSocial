package com.example.postservice.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenapiConfig {
    @Bean
    public OpenAPI postServiceAPI() {
        return new OpenAPI().info(new Info().title("Post Service").description("API for Post Service").version("v0.0.1").license(new License().name("Apache 2.0"))).externalDocs(new ExternalDocumentation().description("u can refer to the documentation about Post Service").url("https://post-service-dummu-url.com/docs"));
    }
}
