package com.example.postservice.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private Long postId;
    private Long userId;
    private Timestamp createdAt;
}
