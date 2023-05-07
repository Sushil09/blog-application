package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CommentMapper;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public List<CommentDto> getAllComments(long postId) {
        Post fetchedPost = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Set<Comment> fetchedComments = fetchedPost.getComments();
        List<CommentDto> comments = fetchedComments.stream().map(comment -> CommentMapper.COMMENT_MAPPER.commentToCommentDto(comment)).collect(Collectors.toList());
        return comments;
    }

    @Override
    public CommentDto saveComment(long postId, CommentDto commentDto) {
        Comment commentToSave = CommentMapper.COMMENT_MAPPER.CommentDtoToComment(commentDto);
        Post fetchedPost = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        commentToSave.setPost(fetchedPost);
        commentRepository.save(commentToSave);

        CommentDto commentSaved = CommentMapper.COMMENT_MAPPER.commentToCommentDto(commentToSave);
        return commentSaved;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post fetchedPost = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment fetchedComment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        if(!fetchedComment.getPost().getId().equals(fetchedPost.getId()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

        return CommentMapper.COMMENT_MAPPER.commentToCommentDto(fetchedComment);
    }

    @Override
    public CommentDto updateComment(long commentId, CommentDto commentDto) {
        Comment fetchedComment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        fetchedComment.setName(commentDto.getName());
        fetchedComment.setBody(commentDto.getBody());
        fetchedComment.setEmail(commentDto.getEmail());
        Comment commentSaved = commentRepository.save(fetchedComment);
        return CommentMapper.COMMENT_MAPPER.commentToCommentDto(commentSaved);

    }

    @Override
    public CommentDto deleteCommentById(long commentId) {
        Comment fetchedComment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        commentRepository.deleteById(commentId);
        return CommentMapper.COMMENT_MAPPER.commentToCommentDto(fetchedComment);
    }
}
