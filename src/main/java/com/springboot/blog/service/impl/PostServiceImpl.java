package com.springboot.blog.service.impl;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.dto.PostMapper;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post postToSave = PostMapper.POST_MAPPER.postDtoToPost(postDto);
        postRepository.save(postToSave);
        PostDto savedPost = PostMapper.POST_MAPPER.postToPostDto(postToSave);
        return savedPost;
    }

    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> postPage = postRepository.findAll(pageRequest);
        List<Post> postList = postPage.getContent();
        List<PostDto> postDtoList = postList.stream().map(post -> PostMapper.POST_MAPPER.postToPostDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setPostList(postDtoList);
        postResponse.setPageNumber(pageNumber);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLast(postPage.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post fetchedPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        PostDto postToReturn = PostMapper.POST_MAPPER.postToPostDto(fetchedPost);
        return postToReturn;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post fetchedPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        fetchedPost.setTitle(postDto.getTitle());
        fetchedPost.setContent(postDto.getContent());
        fetchedPost.setDescription(postDto.getDescription());
        Post savedPost = postRepository.save(fetchedPost);

        PostDto postToReturn = PostMapper.POST_MAPPER.postToPostDto(savedPost);
        return postToReturn;
    }

    @Override
    public PostDto deletePost(Long id) {
        Post fetchedPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        postRepository.deleteById(id);

        return PostMapper.POST_MAPPER.postToPostDto(fetchedPost);
    }


}
