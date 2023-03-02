package com.pragma.powerup.application.plate.mapper;

import com.pragma.powerup.application.plate.dto.request.PlateRequestDto;
import com.pragma.powerup.application.plate.dto.request.PlateUpdateRequestDto;
import com.pragma.powerup.domain.plate.model.PlateModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPlateRequestMapper {
    PlateModel toPlate(PlateRequestDto plateRequestDto);

    PlateModel toPlateUpdate(PlateUpdateRequestDto plateUpdateRequestDto);
}
