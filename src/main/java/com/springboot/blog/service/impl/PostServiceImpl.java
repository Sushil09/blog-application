package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.dto.PostMapper;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.mapstruct.Mapper;
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

    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category fetchedCategory = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", postDto.getCategoryId()));

        Post postToSave = PostMapper.POST_MAPPER.postDtoToPost(postDto);
        postToSave.setCategory(fetchedCategory);
        Post savedPost = postRepository.save(postToSave);

        PostDto postToReturn = PostMapper.POST_MAPPER.postToPostDto(savedPost);
//        postToReturn.setCategoryId(savedPost.getCategory().getId());
        return postToReturn;
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
        Category fetchedCategory = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", postDto.getCategoryId()));
        fetchedPost.setTitle(postDto.getTitle());
        fetchedPost.setContent(postDto.getContent());
        fetchedPost.setDescription(postDto.getDescription());
        fetchedPost.setCategory(fetchedCategory);
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

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Id", categoryId));
        List<Post> postList = postRepository.findPostsByCategoryId(categoryId);
        return postList.stream().map(post ->
                        PostMapper.POST_MAPPER.postToPostDto(post)).
                collect(Collectors.toList());

    }
}
