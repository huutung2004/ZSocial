package com.example.postservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "image_urls", columnDefinition = "JSON")
    private String imageUrls; // Hoặc có thể dùng Map<String, String> nếu cần xử lý phức tạp

    @Enumerated(EnumType.STRING)
    @Column(name = "privacy_level", columnDefinition = "ENUM('public','friends','private')")
    private PrivacyLevel privacyLevel;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    public enum PrivacyLevel {
        PUBLIC,
        FRIENDS,
        PRIVATE
    }
}