package io.mountblue.c26_1java.aravind.blogapplication.controller;

import io.mountblue.c26_1java.aravind.blogapplication.model.Post;
import io.mountblue.c26_1java.aravind.blogapplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/blog-application")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String listPosts(Model model) {
        List<Post> posts = postService.findAll();

        model.addAttribute("posts", posts);

        return "posts-list";
    }

    @GetMapping("/showpost")
    public String showPost(@RequestParam Long id, Model model) {
        Post post = postService.findById(id);

        model.addAttribute("post", post);

        return "view-post";
    }

    @GetMapping("/newpost")
    public String createPost(Model model) {
        Post post = new Post();

        model.addAttribute("post", post);

        return "post-form";
    }

    @GetMapping("editpost")
    public String editPost(@RequestParam Long id, Model model) {
        Post post = postService.findById(id);

        model.addAttribute("post", post);

        return "post-form";
    }

    @PostMapping("/savepost")
    public String savePost(@ModelAttribute("post") Post post) {
        postService.save(post);

        return "redirect:/blog-application/";
    }

    @GetMapping("/deletepost")
    public String deletePost(@RequestParam Long id) {
        postService.deleteById(id);

        return "redirect:/blog-application/";
    }
}