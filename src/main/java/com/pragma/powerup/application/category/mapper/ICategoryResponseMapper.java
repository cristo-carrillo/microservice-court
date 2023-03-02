package com.pragma.powerup.application.category.mapper;

import com.pragma.powerup.application.category.dto.response.CategoryResponseDto;
import com.pragma.powerup.domain.category.model.CategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryResponseMapper {
    CategoryResponseDto toResponse(CategoryModel categoryModel);

    List<CategoryResponseDto> toResponseList(List<CategoryModel> categoryModels);
}
