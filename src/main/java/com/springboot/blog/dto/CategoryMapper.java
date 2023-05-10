package com.springboot.blog.dto;

import com.springboot.blog.entity.Category;
import com.springboot.blog.payload.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper CATEGORY_MAPPER = Mappers.getMapper(CategoryMapper.class);

    CategoryDto categoryToCategoryDto(Category category);

    Category CategoryDtoToCategory(CategoryDto categoryDto);
}
