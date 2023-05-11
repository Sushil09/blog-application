package com.springboot.blog.dto;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    PostMapper POST_MAPPER = Mappers.getMapper(PostMapper.class);

    @Mapping(source = "category.id", target = "categoryId")
    PostDto postToPostDto(Post post);

    Post postDtoToPost(PostDto postDto);
}
