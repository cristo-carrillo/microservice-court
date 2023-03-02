package com.pragma.powerup.application.plate.handler;

import com.pragma.powerup.application.plate.dto.request.PlateRequestDto;
import com.pragma.powerup.application.plate.dto.request.PlateUpdateRequestDto;
import com.pragma.powerup.application.plate.dto.response.PlateListResponseDto;
import com.pragma.powerup.application.plate.dto.response.PlateResponseDto;

import java.util.List;

public interface IPlateHandler {

    void savePlate(PlateRequestDto plateRequestDto);

    PlateResponseDto updatePlate(Long id, PlateUpdateRequestDto plateUpdateRequestDto);

    PlateResponseDto getPlate(Long id);

    PlateResponseDto updateStatusPLate(Long id, Boolean status);

    List<PlateListResponseDto> listPaginatedPlates(Long idRestaurant, Integer numberPage, Integer size);
}
