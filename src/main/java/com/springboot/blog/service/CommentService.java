package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAllComments(long postId);

    CommentDto saveComment(long postId, CommentDto commentDto);

    CommentDto getCommentById(long postId, long commentId);

    CommentDto updateComment(long commentId, CommentDto commentDto);

    CommentDto deleteCommentById(long commentId);

}
