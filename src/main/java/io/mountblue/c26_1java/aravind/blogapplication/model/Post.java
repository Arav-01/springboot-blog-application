package io.mountblue.c26_1java.aravind.blogapplication.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String excerpt;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String author;
    private LocalDateTime publishedAt;
    private boolean isPublished;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Post() {}

    public Post(String title, String excerpt, String content, String author,
                LocalDateTime publishedAt, boolean isPublished,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.excerpt = excerpt;
        this.content = content;
        this.author = author;
        this.publishedAt = publishedAt;
        this.isPublished = isPublished;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Post(String title, String author, String content) {
        this.title = title;
        this.excerpt = title;
        this.content = content;
        this.author = author;
        this.publishedAt = null;
        this.isPublished = true;
        this.createdAt = null;
        this.updatedAt = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
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