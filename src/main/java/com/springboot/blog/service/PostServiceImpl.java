package com.springboot.blog.service;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostMapper;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository){
        this.postRepository=postRepository;
    }
    @Override
    public PostDto createPost(PostDto postDto) {
        Post postToSave = PostMapper.POST_MAPPER.postDtoToPost(postDto);
        postRepository.save(postToSave);
        PostDto savedPost = PostMapper.POST_MAPPER.postToPostDto(postToSave);
        return savedPost;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        List<PostDto> postDtoList = postList.stream().map(post -> PostMapper.POST_MAPPER.postToPostDto(post)).collect(Collectors.toList());
        return postDtoList;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post fetchedPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post","id",id));
        PostDto postToReturn = PostMapper.POST_MAPPER.postToPostDto(fetchedPost);
        return postToReturn;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post fetchedPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post","id",id));
        fetchedPost.setTitle(postDto.getTitle());
        fetchedPost.setContent(postDto.getContent());
        fetchedPost.setDescription(postDto.getDescription());
        Post savedPost = postRepository.save(fetchedPost);

        PostDto postToReturn = PostMapper.POST_MAPPER.postToPostDto(savedPost);
        return postToReturn;
    }

    @Override
    public PostDto deletePost(Long id) {
        Post fetchedPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post","id",id));
        postRepository.deleteById(id);

        return PostMapper.POST_MAPPER.postToPostDto(fetchedPost);
    }


}
