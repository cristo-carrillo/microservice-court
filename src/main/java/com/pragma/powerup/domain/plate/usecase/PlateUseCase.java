package com.pragma.powerup.domain.plate.usecase;

import com.pragma.powerup.domain.category.model.CategoryModel;
import com.pragma.powerup.domain.plate.api.IPlateServicePort;
import com.pragma.powerup.domain.plate.model.PlateModel;
import com.pragma.powerup.domain.plate.spi.IPlatePersistencePort;

import java.util.List;
import java.util.Map;

public class PlateUseCase implements IPlateServicePort {

    private final IPlatePersistencePort platePersistencePort;

    public PlateUseCase(IPlatePersistencePort platePersistencePort) {
        this.platePersistencePort = platePersistencePort;
    }

    @Override
    public void savePlate(PlateModel plateModel) {
        plateModel.setActive(true);
        platePersistencePort.savePlate(plateModel);
    }

    @Override
    public PlateModel updatePlate(Long id, PlateModel plateModel) {
        return platePersistencePort.updatePlate(id, plateModel);
    }

    @Override
    public PlateModel getPlate(Long id) {
        return platePersistencePort.getPlate(id);
    }

    @Override
    public Map<CategoryModel, List<PlateModel>> listPaginatedPlates(Long idRestaurant, Integer numberPage, Integer size) {
        return  platePersistencePort.listPaginatedPlates(idRestaurant, numberPage, size);
    }

    @Override
    public PlateModel updateStatusPlate(Long id, Boolean status) {
        return platePersistencePort.updateStatusPlate(id, status);
    }
}
