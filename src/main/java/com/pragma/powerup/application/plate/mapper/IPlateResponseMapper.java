package com.pragma.powerup.application.plate.mapper;

import com.pragma.powerup.application.category.dto.response.CategoryResponseDto;
import com.pragma.powerup.application.category.mapper.ICategoryResponseMapper;
import com.pragma.powerup.application.plate.dto.response.Plate;
import com.pragma.powerup.application.plate.dto.response.PlateResponseDto;
import com.pragma.powerup.domain.plate.model.PlateModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {ICategoryResponseMapper.class})
public interface IPlateResponseMapper {
    @Mapping(source = "plateModel.name", target = "nameDto")
    @Mapping(source = "plateModel.description", target = "descriptionDto")
    @Mapping(source = "categoryDto.id", target = "category.id")
    @Mapping(source = "categoryDto.name", target = "category.name")
    @Mapping(source = "categoryDto.description", target = "category.description")
    PlateResponseDto toResponse(PlateModel plateModel, CategoryResponseDto categoryDto);

    Plate getPlateDto(PlateModel plateModel);
    List<Plate> getPlateList(List<PlateModel> plates);
}
