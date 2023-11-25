package io.mountblue.c26_1java.aravind.blogapplication.service;

import io.mountblue.c26_1java.aravind.blogapplication.model.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    Set<Tag> getTagSet(String tags);

    Tag findByNameElseGetNew(String name);

    void deleteOrphanedTags();

    List<String> findDistinctTags();
}
