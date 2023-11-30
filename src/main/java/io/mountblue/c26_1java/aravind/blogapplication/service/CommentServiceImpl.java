package io.mountblue.c26_1java.aravind.blogapplication.service;

import io.mountblue.c26_1java.aravind.blogapplication.dao.CommentRepository;
import io.mountblue.c26_1java.aravind.blogapplication.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository postRepository) {
        this.commentRepository = postRepository;
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Comment comment) {
        LocalDateTime now = LocalDateTime.now();

        if(comment.getCreatedAt() == null) comment.setCreatedAt(now);
        comment.setUpdatedAt(now);

        commentRepository.save(comment);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
