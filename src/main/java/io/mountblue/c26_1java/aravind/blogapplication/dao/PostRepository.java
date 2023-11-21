package io.mountblue.c26_1java.aravind.blogapplication.dao;

import io.mountblue.c26_1java.aravind.blogapplication.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}