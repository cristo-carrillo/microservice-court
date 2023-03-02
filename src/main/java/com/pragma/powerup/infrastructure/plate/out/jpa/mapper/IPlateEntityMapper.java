package com.pragma.powerup.infrastructure.plate.out.jpa.mapper;

import com.pragma.powerup.domain.category.model.CategoryModel;
import com.pragma.powerup.domain.plate.model.PlateModel;
import com.pragma.powerup.infrastructure.category.out.jpa.entity.CategoryEntity;
import com.pragma.powerup.infrastructure.plate.out.jpa.entity.PlateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IPlateEntityMapper {
    @Mapping(target = "category", ignore = true)
    PlateEntity toEntity(PlateModel plateModel);
    @Mapping(target = "category", ignore = true)
    PlateModel toPlateModel(PlateEntity plateEntity);
    @Mapping(target = "category", ignore = true)
    List<PlateModel> toPlateModelList(List<PlateEntity> plateEntityList);
    @Mapping(target = "category", ignore = true)
    Map<CategoryModel, List<PlateModel>> toPlatesByCategoryModel(Map<CategoryEntity, List<PlateEntity>> platesByCategory);
}
