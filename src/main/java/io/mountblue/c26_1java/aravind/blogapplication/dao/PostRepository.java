package io.mountblue.c26_1java.aravind.blogapplication.dao;

import io.mountblue.c26_1java.aravind.blogapplication.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findDistinctByTitleContainingOrContentContainingOrAuthorContainingOrTagsNameContaining(String title,
                                                                                                      String content,
                                                                                                      String author,
                                                                                                      String tagName,
                                                                                                      Pageable pageable);
}