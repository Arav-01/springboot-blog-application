package io.mountblue.c26_1java.aravind.blogapplication.service;

import io.mountblue.c26_1java.aravind.blogapplication.dao.TagRepository;
import io.mountblue.c26_1java.aravind.blogapplication.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService{
    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Set<Tag> getTagSet(String tags) {
        return Arrays.stream(tags.split(","))
                     .map(String::trim)
                     .filter(tagName -> !tagName.isEmpty())
                     .map(this::findByNameElseGetNew)
                     .collect(Collectors.toCollection(HashSet::new));
    }

    public Tag findByNameElseGetNew(String name) {
        return tagRepository.findByName(name).orElse(new Tag(name));
    }

    public void deleteOrphanedTags() {
        List<Tag> orphanedTags = tagRepository.findOrphanedTags();

        tagRepository.deleteAll(orphanedTags);
    }
}