package com.pragma.powerup.domain.plate.api;

import com.pragma.powerup.domain.category.model.CategoryModel;
import com.pragma.powerup.domain.plate.model.PlateModel;

import java.util.List;
import java.util.Map;

public interface IPlateServicePort {
    void savePlate(PlateModel plateModel);
    PlateModel updatePlate(Long id,PlateModel plateModel);
    PlateModel getPlate(Long id);
    Map<CategoryModel, List<PlateModel>> listPaginatedPlates(Long idRestaurant, Integer numberPage, Integer size);
    PlateModel updateStatusPlate(Long id, Boolean status);
}
