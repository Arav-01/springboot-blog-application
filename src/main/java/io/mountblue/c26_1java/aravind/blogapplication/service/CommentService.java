package io.mountblue.c26_1java.aravind.blogapplication.service;

import io.mountblue.c26_1java.aravind.blogapplication.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAll();

    List<Comment> findAllByPostId(Long postId);

    Comment findById(Long id);

    void save(Comment comment);

    void deleteById(Long id);
}
