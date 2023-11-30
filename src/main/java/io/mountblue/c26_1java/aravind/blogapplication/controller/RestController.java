package io.mountblue.c26_1java.aravind.blogapplication.controller;

import io.mountblue.c26_1java.aravind.blogapplication.model.Post;
import io.mountblue.c26_1java.aravind.blogapplication.model.User;
import io.mountblue.c26_1java.aravind.blogapplication.service.PostService;
import io.mountblue.c26_1java.aravind.blogapplication.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/blog-application/api")
public class RestController {
    private PostService postService;
    private UserService userService;

    public RestController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/allposts")
    public List<Post> listAllPosts() {
        return postService.findAll();
    }

    @GetMapping("/posts")
    public List<Post> listPosts(@RequestParam(name = "start", defaultValue = "1") int start,
                                @RequestParam(name = "limit", defaultValue = "10") int limit,
                                @RequestParam(name = "sortField", defaultValue = "publishedAt") String sortField,
                                @RequestParam(name = "order", defaultValue = "desc") String sortOrder,
                                @RequestParam(name = "search", defaultValue = "") String search,
                                @RequestParam(name = "authorName", required = false) List<String> authors,
                                @RequestParam(name = "tagName", required = false) List<String> tags) {
        if (authors != null && authors.isEmpty()) authors = null;
        if (tags != null && tags.isEmpty()) tags = null;

        Page<Post> page = postService.findPaginatedAndSortedBySearchAndFilter(
                search, authors, tags, start, limit, sortField, sortOrder);

        return page.getContent();
    }

    @GetMapping("/showpost/{id}")
    public Post showPost(@PathVariable Long id) {
        Post post;
        try {
            post = postService.findById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return post;
    }

    @PostMapping("/createpost")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        try {
            postService.save(post);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @PutMapping("/updatepost/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        Post existingPost;

        try {
        existingPost = postService.findById(id);

        existingPost.setTitle(post.getTitle());
        existingPost.setAuthor(post.getAuthor());
        existingPost.setContent(post.getContent());
        existingPost.setTags(post.getTags());

        postService.save(existingPost);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(existingPost, HttpStatus.OK);
    }

    @DeleteMapping("/deletepost/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        try {
            postService.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Successfully deleted post with id = " + id, HttpStatus.OK);
    }
}
