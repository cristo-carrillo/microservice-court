package com.pragma.powerup.application.category.mapper;

import com.pragma.powerup.application.category.dto.request.CategoryRequestDto;
import com.pragma.powerup.domain.category.model.CategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryRequestMapper {

    CategoryModel toCategory(CategoryRequestDto categoryRequestDto);
}
