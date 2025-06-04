package com.example.postservice.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Data
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    private String passwordHash;

    private String fullName;

    private String avatarUrl;

    private Boolean isVerified;

    private LocalDateTime createdAt;
    private Set<Integer> roles = new HashSet<>();
}
