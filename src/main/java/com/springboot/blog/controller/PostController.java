package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.blog.utils.AppConstants.*;

@EnableMethodSecurity
@RestController
@RequestMapping("/api/posts")
@Slf4j
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        PostDto fetchedPost = postService.createPost(postDto);
        return new ResponseEntity<>(fetchedPost, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false, value = "pageNumber") int pageNumber,
                                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false, value = "pageSize") int pageSize,
                                                    @RequestParam(defaultValue = DEFAULT_SORT_BY, required = false, value = "sortBy") String sortBy,
                                                    @RequestParam(defaultValue = DEFAULT_SORT_DIR, required = false, value = "sortOrder") String sortOrder
    ) {
        return new ResponseEntity<>(postService.getAllPosts(pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("id") Long id) {
        PostDto returnedPost = postService.updatePost(postDto, id);
        return new ResponseEntity<>(returnedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PostDto> deletePostById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postService.deletePost(id), HttpStatus.OK);
    }

    @GetMapping("/category/{Id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("Id") Long categoryId) {
        return new ResponseEntity<>(postService.getPostsByCategory(categoryId), HttpStatus.OK);
    }
}
