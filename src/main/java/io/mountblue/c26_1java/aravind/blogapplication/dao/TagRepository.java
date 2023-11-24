package io.mountblue.c26_1java.aravind.blogapplication.dao;

import io.mountblue.c26_1java.aravind.blogapplication.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    @Query("SELECT t FROM Tag t WHERE t.posts IS EMPTY")
    List<Tag> findOrphanedTags();
}