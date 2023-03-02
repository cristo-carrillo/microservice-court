package com.pragma.powerup.infrastructure.category.out.jpa.mapper;

import com.pragma.powerup.domain.category.model.CategoryModel;
import com.pragma.powerup.infrastructure.category.out.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ICategoryEntityMapper {

    CategoryEntity toEntity(CategoryModel category);
    @Mapping(target = "plates", ignore = true)
    CategoryModel toCategoryModel(CategoryEntity categoryEntity);

    List<CategoryModel> toCategoryModelList(List<CategoryEntity> categoryEntities);
}
