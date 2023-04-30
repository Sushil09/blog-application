package com.springboot.blog.dto;

import com.springboot.blog.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    PostMapper POST_MAPPER = Mappers.getMapper(PostMapper.class);

    PostDto postToPostDto(Post post);

    Post postDtoToPost(PostDto postDto);
}
