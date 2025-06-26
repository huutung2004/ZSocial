package com.example.postservice.dto;

import com.example.postservice.model.Like;
import lombok.Data;

import java.sql.Timestamp;
@Data
public class LikeResponse {
    private Long likeId;
    private Long postId;
    private Long userId;
    private Timestamp createAt;

    public LikeResponse(Like like) {
        this.likeId = like.getLikeId();
        this.postId = like.getPostId();
        this.userId = like.getUserId();
        this.createAt = like.getCreatedAt();
    }

}
