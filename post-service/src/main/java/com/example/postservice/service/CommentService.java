package com.example.postservice.service;

import com.example.postservice.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CommentService {
    private  final CommentRepository commentRepository;

}
