package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortOrder);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    PostDto deletePost (Long id);

    List<PostDto> getPostsByCategory(Long id);

}
