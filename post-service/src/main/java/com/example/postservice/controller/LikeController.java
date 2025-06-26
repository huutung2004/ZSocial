package com.example.postservice.controller;

import com.example.postservice.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/likes")
public class LikeController {
    private final LikeService likeService;
    @PostMapping
    public ResponseEntity<?> likePost(@RequestParam Long userId, @RequestParam Long postId) {
        boolean success = likeService.likePost(userId, postId);
        if (success) {
            return ResponseEntity.ok("Post liked successfully");
        } else {
            return ResponseEntity.badRequest().body("User has already liked this post");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> unlikePost(@RequestParam Long userId, @RequestParam Long postId) {
        boolean success = likeService.unlikePost(userId, postId);
        if (success) {
            return ResponseEntity.ok("Post unliked successfully");
        } else {
            return ResponseEntity.badRequest().body("User hasn't liked this post yet");
        }
    }
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getLikesByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(likeService.getLikesByPostId(postId));
    }

}
