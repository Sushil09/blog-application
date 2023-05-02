package com.springboot.blog.controller;


import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable("postId") long postId) {
        return new ResponseEntity<>(commentService.getAllComments(postId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CommentDto> saveComment(@PathVariable("postId") long postId,
                                                  @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.saveComment(postId, commentDto), HttpStatus.CREATED);

    }

    @GetMapping("{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("commentId") long commentId){
        return new ResponseEntity<>(commentService.getCommentById(commentId),HttpStatus.OK);
    }

    @PutMapping("{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable ("commentId") long commentId,
                                                    @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.updateComment(commentId,commentDto),HttpStatus.OK);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<CommentDto> deleteCommentById(@PathVariable("commentId") long commentId){
        return new ResponseEntity<>(commentService.deleteCommentById(commentId),HttpStatus.OK);
    }
}
