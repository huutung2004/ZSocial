package com.example.postservice.dto;

import lombok.Data;

@Data
public class CommentRequest {
    private Long userId;
    private Long postId;
    private String content;
}
