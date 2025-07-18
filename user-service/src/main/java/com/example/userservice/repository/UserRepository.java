package com.example.userservice.repository;

import com.example.userservice.dto.UserResponse;
import com.example.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByFullNameContainingIgnoreCase(String name);

    boolean findByEmail(String email);

    boolean findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
