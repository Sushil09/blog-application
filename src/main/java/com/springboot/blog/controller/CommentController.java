package com.springboot.blog.controller;


import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@Tag(name = "CRUD REST APIs for Comments Resource")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("")
    @Operation(summary = "API for fetching all comments for a particular post",
            description = "This is used to fetch all comments for a particular post")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Success")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable("postId") long postId) {
        return new ResponseEntity<>(commentService.getAllComments(postId), HttpStatus.OK);
    }

    @PostMapping("")
    @Operation(summary = "API to save a comment against a particular post",
            description = "This is used to save a particular comment against a particular post")
    @ApiResponse(responseCode = "201", description = "Http Status 201 Success")
    public ResponseEntity<CommentDto> saveComment(@PathVariable("postId") long postId,
                                                  @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.saveComment(postId, commentDto), HttpStatus.CREATED);

    }

    @GetMapping("{commentId}")
    @Operation(summary = "API to fetch a single comment against a particular post",
            description = "This is used to fetch a particular comment by id against a particular post")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Success")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId,
                                                     @PathVariable("commentId") long commentId) {
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @PutMapping("{commentId}")
    @GetMapping("{commentId}")
    @Operation(summary = "API to update a single comment against a particular post",
            description = "This is used to update a particular comment by id against a particular post")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Success")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("commentId") long commentId,
                                                    @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.updateComment(commentId, commentDto), HttpStatus.OK);
    }

    @DeleteMapping("{commentId}")
    @Operation(summary = "API to delete a single comment against a particular post",
            description = "This is used to delete a particular comment by id against a particular post")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Success")
    public ResponseEntity<CommentDto> deleteCommentById(@PathVariable("commentId") long commentId) {
        return new ResponseEntity<>(commentService.deleteCommentById(commentId), HttpStatus.OK);
    }
}
