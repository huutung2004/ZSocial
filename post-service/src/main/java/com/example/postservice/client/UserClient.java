package com.example.postservice.client;
import com.example.postservice.dto.UserResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface UserClient {
    Logger log = LoggerFactory.getLogger(UserClient.class);

    @GetExchange("/api/v1/user/{userId}")
    @CircuitBreaker(name = "user", fallbackMethod = "getUserByIdFallback")
    @Retry(name = "user")
    ResponseEntity<UserResponse> getUserById(@PathVariable("userId") Long userId);

    default ResponseEntity<UserResponse> getUserByIdFallback(Long userId, Throwable throwable) {
        log.error("Cannot get user by user id: {}. Error: {}", userId, throwable.getMessage());
        return ResponseEntity.internalServerError().build();
    }
}