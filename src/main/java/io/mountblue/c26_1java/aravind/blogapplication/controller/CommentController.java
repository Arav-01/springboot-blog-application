package io.mountblue.c26_1java.aravind.blogapplication.controller;

import io.mountblue.c26_1java.aravind.blogapplication.model.Comment;
import io.mountblue.c26_1java.aravind.blogapplication.model.Post;
import io.mountblue.c26_1java.aravind.blogapplication.service.CommentService;
import io.mountblue.c26_1java.aravind.blogapplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/blog-application")
public class CommentController {
    private PostService postService;
    private CommentService commentService;

    @Autowired
    public CommentController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("/savecomment")
    public String savePost(@ModelAttribute("commentObj") Comment comment, @RequestParam("postId") Long postId) {
        Post post = postService.findById(postId);
        post.addComment(comment);

        postService.save(post);

        return "redirect:/blog-application/showpost?id=" + postId;
    }

    @GetMapping("/editcomment")
    public String editComment(@RequestParam Long id, @RequestParam Long postId, Model model) {
        Comment comment = commentService.findById(id);

        model.addAttribute("commentObj", comment);
        model.addAttribute("postId", postId);

        return "comment-form";
    }

    @PostMapping("/updatecomment")
    public String updateComment(@ModelAttribute("commentObj") Comment comment, @RequestParam Long postId) {
        commentService.save(comment);

        return "redirect:/blog-application/showpost?id=" + postId;
    }

    @GetMapping("/deletecomment")
    public String deleteComment(@RequestParam Long id, @RequestParam Long postId) {
        commentService.deleteById(id);

        return "redirect:/blog-application/showpost?id=" + postId;
    }
}
