package com.example.userservice.service;

import com.example.userservice.dto.UserResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUserService {
    public UserResponse findUserById(Long id);
    public void deleteUserById(Long id);
    public List<UserResponse> findUserByName(String name);

    @Transactional(readOnly = true)
    List<UserResponse> getAllUsers();
}
