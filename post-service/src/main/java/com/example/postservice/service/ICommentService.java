package com.example.postservice.service;

import com.example.postservice.dto.CommentRequest;
import com.example.postservice.dto.CommentResponse;
import com.example.postservice.model.Comment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICommentService {
    public CommentResponse insertComment(CommentRequest commentRequest);
    public List<CommentResponse> getComentByPostId(Long postId);
    public boolean deleteComment(Long commentId);
}
