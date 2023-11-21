package io.mountblue.c26_1java.aravind.blogapplication.controller;

import io.mountblue.c26_1java.aravind.blogapplication.model.Comment;
import io.mountblue.c26_1java.aravind.blogapplication.model.Post;
import io.mountblue.c26_1java.aravind.blogapplication.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/blog-application")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/savecomment")
    public String savePost(@ModelAttribute("commentObj") Comment comment) {
        commentService.save(comment);

        return "redirect:/blog-application/showpost?id=" + comment.getPostId();
    }

    @GetMapping("/newcomment")
    public String createComment(@RequestParam Long postId, Model model) {
        Comment comment = new Comment();
        comment.setPostId(postId);

        model.addAttribute("commentObj", comment);

        return "comment-form";
    }

    @GetMapping("/editcomment")
    public String editComment(@RequestParam Long id, Model model) {
        Comment comment = commentService.findById(id);

        model.addAttribute("commentObj", comment);

        return "comment-form";
    }

    @GetMapping("/deletecomment")
    public String deleteComment(@RequestParam Long id,
                                @RequestParam Long postId) {
        commentService.deleteById(id);

        return "redirect:/blog-application/showpost?id=" + postId;
    }
}
