package com.example.postservice.service;

import com.example.postservice.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class LikeService {
    private final LikeRepository likeRepository;
}
