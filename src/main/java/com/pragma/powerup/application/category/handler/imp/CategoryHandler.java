package com.pragma.powerup.application.category.handler.imp;

import com.pragma.powerup.application.category.dto.request.CategoryRequestDto;
import com.pragma.powerup.application.category.dto.response.CategoryResponseDto;
import com.pragma.powerup.application.category.handler.ICategoryHandler;
import com.pragma.powerup.application.category.mapper.ICategoryRequestMapper;
import com.pragma.powerup.application.category.mapper.ICategoryResponseMapper;
import com.pragma.powerup.domain.category.api.ICategoryServicePort;
import com.pragma.powerup.domain.category.model.CategoryModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {

    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;
    private final ICategoryResponseMapper categoryResponseMapper;

    @Override
    public void saveCategory(CategoryRequestDto categoryRequestDto) {
        CategoryModel categoryModel = categoryRequestMapper.toCategory(categoryRequestDto);
        categoryServicePort.saveCategory(categoryModel);
    }

    @Override
    public List<CategoryResponseDto> getAllCategorys() {
        return categoryResponseMapper.toResponseList(categoryServicePort.getAllCategorys());
    }

    @Override
    public CategoryResponseDto getCategory(Long id) {
        return categoryResponseMapper.toResponse(categoryServicePort.getCategory(id));
    }
}
