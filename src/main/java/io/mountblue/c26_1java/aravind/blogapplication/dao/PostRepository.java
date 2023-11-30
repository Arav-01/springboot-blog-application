package io.mountblue.c26_1java.aravind.blogapplication.dao;

import io.mountblue.c26_1java.aravind.blogapplication.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findDistinctByTitleContainingOrContentContainingOrAuthorContainingOrTagsNameContaining(String title,
                                                                                                      String content,
                                                                                                      String author,
                                                                                                      String tagName,
                                                                                                      Pageable pageable);

    @Query("""
            SELECT DISTINCT p
            FROM Post p LEFT JOIN p.tags t
            WHERE (p.title LIKE %:search%
                   OR p.content LIKE %:search%
                   OR p.author LIKE %:search%
                   OR t.name LIKE %:search%)
            AND (:authors IS NULL OR p.author IN :authors)
            AND (:tags IS NULL OR t.name IN :tags)
            """)
    Page<Post> findPaginatedAndSortedBySearchAndFilter(@Param("search") String search,
                                                       @Param("authors") List<String> authors,
                                                       @Param("tags") List<String> tags,
                                                       Pageable pageable);

    @Query("SELECT DISTINCT author FROM Post ORDER BY author")
    List<String> findDistinctAuthors();
}