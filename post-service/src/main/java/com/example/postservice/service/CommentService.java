package com.example.postservice.service;

import com.example.postservice.client.UserClient;
import com.example.postservice.dto.CommentRequest;
import com.example.postservice.dto.CommentResponse;
import com.example.postservice.dto.UserResponse;
import com.example.postservice.exception.IsNotExistException;
import com.example.postservice.model.Comment;
import com.example.postservice.model.Post;
import com.example.postservice.repository.CommentRepository;
import com.example.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CommentService implements ICommentService {
    private  final CommentRepository commentRepository;
    private  final PostService postService;
    @Autowired
    private UserClient userClient;
    @Transactional
    @Override
    public CommentResponse insertComment(CommentRequest commentRequest) {
        Post post = postService.findById(commentRequest.getPostId());

        ResponseEntity<UserResponse> userResponse = userClient.getUserById(commentRequest.getUserId());
        if (!userResponse.getStatusCode().is2xxSuccessful()){
            throw new RuntimeException("User not found");
        }
        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .postId(commentRequest.getPostId())
                .userId(commentRequest.getUserId())
                .build();
        commentRepository.save(comment);
        return mapCommentToCommentResponse(comment);
    }

    @Override
    public List<CommentResponse> getComentByPostId(Long postId) {
        try {
            List<Comment> comments = commentRepository.findByPostId(postId);
            return comments.stream()
                    .map(this::mapCommentToCommentResponse)
                    .toList();
        } catch (IsNotExistException e) {
            throw new IsNotExistException("Not Found Comments in Post: "+postId);
        }
    }

    @Override
    public boolean deleteComment(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
            return true;
        }else throw new IsNotExistException("Not Found Comments: "+commentId);
    }

    private CommentResponse mapCommentToCommentResponse(Comment comment){
        return CommentResponse.builder()
                .id(comment.getCommentId())
                .postId(comment.getPostId())
                .userId(comment.getUserId())
                .createdAt(comment.getCreatedAt())
                .content(comment.getContent())
                .build();
    }
}
