package com.springboot.blog.dto;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper COMMENT_MAPPER = Mappers.getMapper(CommentMapper.class);

    CommentDto commentToCommentDto(Comment comment);

    Comment CommentDtoToComment(CommentDto commentDto);
}
