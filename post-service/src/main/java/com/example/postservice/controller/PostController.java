package com.example.postservice.controller;

import com.example.postservice.client.UserClient;
import com.example.postservice.dto.UserResponse;
import com.example.postservice.exception.IsNotExistException;
import com.example.postservice.model.Post;
import com.example.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("api/v1/posts")
public class PostController {
    @Autowired
    private UserClient userClient;
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPostById(@PathVariable("id") Long id){
        try {
            Post post = postService.findById(id);
            return ResponseEntity.ok().body(post);
        } catch (IsNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping()
    public ResponseEntity<?> createPost(@RequestBody Post post){
        try {
            ResponseEntity<UserResponse> userResponse = userClient.getUserById(post.getUserId());
            if (userResponse.getStatusCode().is2xxSuccessful() && userResponse.getBody() != null) {
                postService.createPost(post);
                return ResponseEntity.status(HttpStatus.CREATED).body(post);
            } else {
                throw new RuntimeException("User not found");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping()
    public ResponseEntity<?> updatePost(@RequestBody Post post){
        try {
            postService.updatePost(post);
            return ResponseEntity.status(HttpStatus.OK).body(post);
        } catch (IsNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id){
        try {
            postService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (IsNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/user/{user_Id}")
    public ResponseEntity<?> findUserById(@PathVariable("user_Id") Long user_Id){
        try {
            List<Post> listPostOfUser = postService.findPostByUserId(user_Id);
            return ResponseEntity.ok().body(listPostOfUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
