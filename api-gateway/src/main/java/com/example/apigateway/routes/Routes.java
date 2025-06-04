package com.example.apigateway.routes;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.*;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;


@Configuration
public class Routes {
    //ket noi den user-service
    @Bean
    public RouterFunction<ServerResponse> userServerRoute(){
        return GatewayRouterFunctions.route("user_service").route(RequestPredicates.path("api/v1/user/**"), HandlerFunctions.http("http://localhost:8081"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("userServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> userSwaggerRoute(){
        return  GatewayRouterFunctions.route("user_service_swagger").route(RequestPredicates.path("/aggregate/User-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8081"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("userSwaggerCircuitBreaker", URI.create("forward:/fallbackRoute")))

                .filter(setPath("/api-docs"))
                .build();
    }
    //ket noi den post-service
    @Bean
    public RouterFunction<ServerResponse> postServerRoute(){
        return GatewayRouterFunctions.route("post_service").route(RequestPredicates.path("api/v1/posts/**"), HandlerFunctions.http("http://localhost:8082"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("postServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> postSwaggerRoute(){
        return  GatewayRouterFunctions.route("post_service_swagger").route(RequestPredicates.path("/aggregate/Post-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8082"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("postSwaggerCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .filter(setPath("/api-docs"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> fallBackRoute(){
        return GatewayRouterFunctions.route("fallbackRouter").GET("/fallbackRoute",request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).body("Server Unvaiable, please try again later"))
                .build();
    }
}
