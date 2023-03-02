package com.pragma.powerup.application.plate.handler.imp;

import com.pragma.powerup.application.category.mapper.ICategoryResponseMapper;
import com.pragma.powerup.application.plate.dto.request.PlateRequestDto;
import com.pragma.powerup.application.plate.dto.request.PlateUpdateRequestDto;
import com.pragma.powerup.application.plate.dto.response.PlateListResponseDto;
import com.pragma.powerup.application.plate.dto.response.PlateResponseDto;
import com.pragma.powerup.application.plate.handler.IPlateHandler;
import com.pragma.powerup.application.plate.mapper.IPlateRequestMapper;
import com.pragma.powerup.application.plate.mapper.IPlateResponseMapper;
import com.pragma.powerup.domain.category.api.ICategoryServicePort;
import com.pragma.powerup.domain.category.model.CategoryModel;
import com.pragma.powerup.domain.plate.api.IPlateServicePort;
import com.pragma.powerup.domain.plate.model.PlateModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class PlateHandler implements IPlateHandler {

    private final IPlateServicePort plateServicePort;
    private final IPlateRequestMapper plateRequestMapper;
    private final IPlateResponseMapper plateResponseMapper;

    private final ICategoryServicePort categoryServicePort;
    private final ICategoryResponseMapper categoryResponseMapper;

    @Override
    public void savePlate(PlateRequestDto plateRequestDto) {
        PlateModel plateModel = plateRequestMapper.toPlate(plateRequestDto);
        plateServicePort.savePlate(plateModel);
    }

    @Override
    public PlateResponseDto updatePlate(Long id, PlateUpdateRequestDto plateUpdateRequestDto) {
//         = plateServicePort.getPlate(id);
        PlateModel plateModel = plateRequestMapper.toPlateUpdate(plateUpdateRequestDto);
        PlateModel plate = plateServicePort.updatePlate(id, plateModel);
        CategoryModel categoryModel = categoryServicePort.getCategory(plate.getIdCategory());
        return plateResponseMapper.toResponse(plate, categoryResponseMapper.toResponse(categoryModel));
    }



    @Override
    public PlateResponseDto getPlate(Long id) {
        PlateModel plateModel = plateServicePort.getPlate(id);
        CategoryModel categoryModel = categoryServicePort.getCategory(plateModel.getIdCategory());
        return plateResponseMapper.toResponse(plateModel, categoryResponseMapper.toResponse(categoryModel));
    }

    @Override
    public PlateResponseDto updateStatusPLate(Long id, Boolean status) {
        PlateModel plate = plateServicePort.updateStatusPlate(id, status);
        CategoryModel categoryModel = categoryServicePort.getCategory(plate.getIdCategory());
        return plateResponseMapper.toResponse(plate, categoryResponseMapper.toResponse(categoryModel));
    }

    @Override
    public List<PlateListResponseDto> listPaginatedPlates(Long idRestaurant, Integer numberPage, Integer size) {
        Map<CategoryModel, List<PlateModel>> plate = plateServicePort.listPaginatedPlates(idRestaurant, numberPage, size);
        List<PlateListResponseDto> plateListResponse = new ArrayList<>();
        for(Map.Entry<CategoryModel, List<PlateModel>> entry: plate.entrySet()){
            PlateListResponseDto pl = new PlateListResponseDto();
            pl.setNameCategory(entry.getKey().getName());
            pl.setPlate(plateResponseMapper.getPlateList(entry.getValue()));
            plateListResponse.add(pl);
        }

        return plateListResponse;
    }

}
