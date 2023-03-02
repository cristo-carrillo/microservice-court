package com.pragma.powerup.application.category.handler;

import com.pragma.powerup.application.category.dto.request.CategoryRequestDto;
import com.pragma.powerup.application.category.dto.response.CategoryResponseDto;

import java.util.List;

public interface ICategoryHandler {

    void saveCategory(CategoryRequestDto categoryRequestDto);

    List<CategoryResponseDto> getAllCategorys();

    CategoryResponseDto getCategory(Long id);
}
