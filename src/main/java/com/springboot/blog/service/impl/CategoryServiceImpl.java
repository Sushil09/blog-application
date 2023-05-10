package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CategoryMapper;
import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category categoryToSave = categoryRepository.save(CategoryMapper.CATEGORY_MAPPER.CategoryDtoToCategory(categoryDto));
        return CategoryMapper.CATEGORY_MAPPER.categoryToCategoryDto(categoryToSave);
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category","Id",id));
        return CategoryMapper.CATEGORY_MAPPER.categoryToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(category ->
                CategoryMapper.CATEGORY_MAPPER.categoryToCategoryDto(category))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category","Id",id));
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return CategoryMapper.CATEGORY_MAPPER.categoryToCategoryDto(updatedCategory);

    }

    @Override
    public CategoryDto deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category","Id",id));
        categoryRepository.delete(category);
        return CategoryMapper.CATEGORY_MAPPER.categoryToCategoryDto(category);
    }
}
