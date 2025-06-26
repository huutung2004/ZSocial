package com.example.postservice.service;

import com.example.postservice.exception.IsNotExistException;
import com.example.postservice.model.Post;
import com.example.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService implements IPostService {
    private final PostRepository postRepository;
    public void createPost(Post post){
        postRepository.save(post);
    }

    @Override
    public Post findById(Long id) {
        if(postRepository.existsById(id)){
            return postRepository.findById(id).get();
        }else{
            throw new IsNotExistException("Not Found Post");
        }
    }

    @Override
    public void updatePost(Post post) {
        if(postRepository.existsById(post.getPostId())){
            postRepository.save(post);
        }else {
            throw new IsNotExistException("Not Found Post");
        }
    }

    @Override
    public void deleteById(Long id) {
        if(postRepository.existsById(id)){
            postRepository.deleteById(id);
        }else {
            throw new IsNotExistException("Not Found Post");
        }
    }

    @Override
    public List<Post> findPostByUserId(Long userId) {
       List<Post> findPostByUserId = postRepository.findByUserId(userId) ;
       if(!findPostByUserId.isEmpty()){
           return findPostByUserId;
       }else {
           throw new IsNotExistException("Not Found Post");
       }
    }


}
