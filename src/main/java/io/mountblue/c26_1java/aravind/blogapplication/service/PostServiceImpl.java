package io.mountblue.c26_1java.aravind.blogapplication.service;

import io.mountblue.c26_1java.aravind.blogapplication.dao.PostRepository;
import io.mountblue.c26_1java.aravind.blogapplication.model.Post;
import io.mountblue.c26_1java.aravind.blogapplication.exception.ResourceNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(Long id) {
         return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No post with id = " + id));
    }

    @Override
    public void save(Post post) {
        LocalDateTime now = LocalDateTime.now();

        if(post.getCreatedAt() == null) post.setCreatedAt(now);
        if(post.getPublishedAt() == null) post.setPublishedAt(now);
        post.setUpdatedAt(now);
        post.setPublished(true);
        post.setExcerpt(StringUtils.abbreviate(post.getContent(), 255));

        postRepository.save(post);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<String> findDistinctAuthors() {
        return postRepository.findDistinctAuthors();
    }

    @Override
    public Page<Post> findPaginatedAndSortedBySearchAndFilter(String search,
                                                              List<String> authors,
                                                              List<String> tags,
                                                              int start, int limit,
                                                              String sortField, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of((start-1)/limit, limit, sort);

        return postRepository
                .findPaginatedAndSortedBySearchAndFilter(
                        search, authors, tags, pageable
                );
    }
}