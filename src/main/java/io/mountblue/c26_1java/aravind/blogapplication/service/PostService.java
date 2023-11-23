package io.mountblue.c26_1java.aravind.blogapplication.service;

import io.mountblue.c26_1java.aravind.blogapplication.model.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    List<Post> findAll();

    Post findById(Long id);

    void save(Post post);

    void deleteById(Long id);

    Page<Post> findPaginated(int start, int limit);
}