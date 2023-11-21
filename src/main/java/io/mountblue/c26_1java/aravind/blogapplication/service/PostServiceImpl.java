package io.mountblue.c26_1java.aravind.blogapplication.service;

import io.mountblue.c26_1java.aravind.blogapplication.dao.PostRepository;
import io.mountblue.c26_1java.aravind.blogapplication.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(Long id) {
        // return postRepository.findById(id).orElseThrow();

        Optional<Post> schrodingerPost = postRepository.findById(id);

        Post post = null;
        if (schrodingerPost.isPresent()) {
            post = schrodingerPost.get();
        } else {
            throw new RuntimeException("Did not find employee id - " + id);
        }

        return post;
    }

    @Override
    public void save(Post theEmployee) {
        postRepository.save(theEmployee);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}