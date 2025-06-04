package com.example.postservice.service;

import com.example.postservice.model.Post;

import java.util.List;

public interface IPostService {
    public Post findById(Long id);
    public void updatePost(Post post);
    public void deleteById(Long id);
    public List<Post> findPostByUserId(Long userId);

}
