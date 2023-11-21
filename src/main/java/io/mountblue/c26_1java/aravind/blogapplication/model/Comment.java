package io.mountblue.c26_1java.aravind.blogapplication.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Comment() {}

    public Comment(String name, String email, String comment, Post post,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.email = email;
        this.comment = comment;
        this.post = post;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}