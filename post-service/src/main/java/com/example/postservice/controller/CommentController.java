package com.example.postservice.controller;

import com.example.postservice.client.UserClient;
import com.example.postservice.dto.CommentRequest;
import com.example.postservice.dto.CommentResponse;
import com.example.postservice.dto.UserResponse;
import com.example.postservice.exception.IsNotExistException;
import com.example.postservice.model.Comment;
import com.example.postservice.model.Post;
import com.example.postservice.service.CommentService;
import com.example.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;
    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentRequest commentRequest) {
        try {
            CommentResponse commentResponse = commentService.insertComment(commentRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
        } catch (IsNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/posts/{post_id}")
    public ResponseEntity<?> getPostComments(@PathVariable("post_id") Long postId) {
        try {
            List<CommentResponse> commentResponses = commentService.getComentByPostId(postId);
            return ResponseEntity.status(HttpStatus.OK).body(commentResponses);
        } catch (IsNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IsNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
