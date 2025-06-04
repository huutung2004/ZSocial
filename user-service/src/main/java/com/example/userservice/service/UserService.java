package com.example.userservice.service;

import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.exception.DuplicateResourceException;
import com.example.userservice.exception.NotFoundException;
import com.example.userservice.model.Role;
import com.example.userservice.model.User;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Transactional
    public void placeUser(UserRequest userRequest) {
        // Tách biệt logic tạo mới và cập nhật
        if (userRequest.id() == null) {
            createNewUser(userRequest);
        } else {
            updateExistingUser(userRequest);
        }
    }

    private void createNewUser(UserRequest userRequest) {
        // Kiểm tra email đã tồn tại chưa
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new DuplicateResourceException("Email already exists: " + userRequest.email());
        }

        // Kiểm tra username đã tồn tại chưa
        if (userRepository.existsByUsername(userRequest.username())) {
            throw new DuplicateResourceException("Username already exists: " + userRequest.username());
        }

        User user = new User();
        mapUserRequestToEntity(userRequest, user);
        userRepository.save(user);
    }

    private void updateExistingUser(UserRequest userRequest) {
        User user = userRepository.findById(userRequest.id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Kiểm tra version nếu cần
        // if (userRequest.version() != null && !userRequest.version().equals(user.getVersion())) {
        //     throw new OptimisticLockingFailureException("User has been modified by another transaction");
        // }

        mapUserRequestToEntity(userRequest, user);
        userRepository.save(user);
    }

    private void mapUserRequestToEntity(UserRequest userRequest, User user) {
        user.setEmail(userRequest.email());
        Set<Role> roleEntities = userRequest.roles().stream()
                .map(id -> roleRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Role not found")))
                .collect(Collectors.toSet());
        user.setRoles(roleEntities);
        user.setFullName(userRequest.fullName());
        user.setCreatedAt(userRequest.createdAt());
        user.setPasswordHash(userRequest.passwordHash());
        user.setIsVerified(userRequest.isVerified());
        user.setAvatarUrl(userRequest.avatarUrl());
        user.setUsername(userRequest.username());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        return mapUserToUserResponse(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findUserByName(String name) {
        List<User> users = userRepository.findByFullNameContainingIgnoreCase(name);
        return users.stream()
                .map(this::mapUserToUserResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapUserToUserResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapUserToUserResponse(User user) {
        Set<Integer> roleIds = user.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toSet());

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getFullName(),
                user.getAvatarUrl(),
                user.getIsVerified(),
                user.getCreatedAt(),
                roleIds
        );
    }
}
