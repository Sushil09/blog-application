package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "CRUD REST APIs for Post Resource")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Create POST REST API",
            description = "Used to save post into database")
    @ApiResponse(responseCode = "201", description = "Http Status 201 Created")
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        PostDto fetchedPost = postService.createPost(postDto);
        return new ResponseEntity<>(fetchedPost, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all POST REST API",
            description = "Used to fetch all posts from database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Success")
    @GetMapping("")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false, value = "pageNumber") int pageNumber,
                                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false, value = "pageSize") int pageSize,
                                                    @RequestParam(defaultValue = DEFAULT_SORT_BY, required = false, value = "sortBy") String sortBy,
                                                    @RequestParam(defaultValue = DEFAULT_SORT_DIR, required = false, value = "sortOrder") String sortOrder
    ) {
        return new ResponseEntity<>(postService.getAllPosts(pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    @Operation(summary = "GET POST by id REST API",
            description = "Used to fetch a post by Id from database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Success")
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @Operation(summary = "UPDATE POST by id REST API",
            description = "Used to update a post by Id from database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Success")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("id") Long id) {
        PostDto returnedPost = postService.updatePost(postDto, id);
        return new ResponseEntity<>(returnedPost, HttpStatus.OK);
    }

    @Operation(summary = "DELETE POST by id REST API",
            description = "Used to delete a post by Id from database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Success")
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
