package com.example.userservice.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record UserRequest(Long id, String username, String email, String passwordHash, String fullName, String avatarUrl, Boolean isVerified, LocalDateTime createdAt,
                          Set<Integer> roles) {
}

