package com.example.postservice.service;

import com.example.postservice.dto.LikeResponse;
import com.example.postservice.model.Like;
import com.example.postservice.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {
    @Autowired
    private final LikeRepository likeRepository;
    @Transactional
    public boolean likePost(Long userId, Long postId) {
        if (likeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            return false; // đã like rồi
        }

        Like like = Like.builder()
                .userId(userId)
                .postId(postId)
                .build();

        likeRepository.save(like);
        return true;
    }
    @Transactional
    public boolean unlikePost(Long userId, Long postId) {
        if (likeRepository.findByUserIdAndPostId(userId, postId).isEmpty()) {
            return false; // chưa like
        }

        likeRepository.deleteByUserIdAndPostId(userId, postId);
        return true;
    }
    public List<LikeResponse> getLikesByPostId(Long postId) {
        List<Like> likes =  likeRepository.findAllByPostId(postId);
        return likes.stream().map(LikeResponse::new).collect(Collectors.toList());
    }

}
