package io.mountblue.c26_1java.aravind.blogapplication.controller;

import io.mountblue.c26_1java.aravind.blogapplication.model.Comment;
import io.mountblue.c26_1java.aravind.blogapplication.model.Post;
import io.mountblue.c26_1java.aravind.blogapplication.model.Tag;
import io.mountblue.c26_1java.aravind.blogapplication.service.CommentService;
import io.mountblue.c26_1java.aravind.blogapplication.service.PostService;
import io.mountblue.c26_1java.aravind.blogapplication.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/blog-application")
public class PostController {
    private PostService postService;
    private CommentService commentService;
    private TagService tagService;

    @Autowired
    public PostController(PostService postService, CommentService commentService, TagService tagService) {
        this.postService = postService;
        this.commentService = commentService;
        this.tagService = tagService;
    }

    @GetMapping("/")
    public String listPosts(@RequestParam(name = "start", defaultValue = "1") int start,
                            @RequestParam(name = "limit", defaultValue = "10") int limit,
                            @RequestParam(name = "sortField", defaultValue = "publishedAt") String sortField,
                            @RequestParam(name = "order", defaultValue = "desc") String sortOrder,
                            @RequestParam(name = "search", defaultValue = "") String search,
                            @RequestParam(name = "authorName", required = false) List<String> authors,
                            @RequestParam(name = "tagName", required = false) List<String> tags,
                            Model model) {
        if (authors != null && authors.isEmpty()) authors = null;
        if (tags != null && tags.isEmpty()) tags = null;

        Page<Post> page = postService.findPaginatedAndSortedBySearchAndFilter(
                search, authors, tags, start, limit, sortField, sortOrder);

        model.addAttribute("currentPage", 1 + (start-1) / limit);
        model.addAttribute("postsPerPage", limit);
        model.addAttribute("totalPages", page.getTotalPages());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);

        model.addAttribute("search", search);

        model.addAttribute("authorName", authors);
        model.addAttribute("allDistinctAuthors", postService.findDistinctAuthors());
        model.addAttribute("tagName", tags);
        model.addAttribute("allDistinctTags", tagService.findDistinctTags());

        model.addAttribute("posts", page.getContent());

        return "posts-list";
    }

    @PostMapping("/savepost")
    public String savePost(@ModelAttribute("post") Post post, @RequestParam("postTags") String tags) {
        Set<Tag> tagSet = tagService.getTagSet(tags);
        post.setTags(tagSet);

        postService.save(post);

        return "redirect:/blog-application/";
    }

    @GetMapping("/showpost")
    public String showPost(@RequestParam Long id, @RequestParam(required = false) Long commentId, Model model) {
        Post post = postService.findById(id);
        Comment comment = commentId == null ? new Comment() : commentService.findById(commentId);

        model.addAttribute("post", post);
        model.addAttribute("commentObj", comment);

        return "view-post";
    }

    @GetMapping("/newpost")
    public String createPost(Model model) {
        Post post = new Post();

        model.addAttribute("post", post);

        return "post-form";
    }

    @GetMapping("/editpost")
    public String editPost(@RequestParam Long id, Model model) {
        Post post = postService.findById(id);

        model.addAttribute("post", post);

        return "post-form";
    }

    @GetMapping("/deletepost")
    public String deletePost(@RequestParam Long id) {
        postService.deleteById(id);
        tagService.deleteOrphanedTags();

        return "redirect:/blog-application/";
    }
}