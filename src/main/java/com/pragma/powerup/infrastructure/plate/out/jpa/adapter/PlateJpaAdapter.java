package com.pragma.powerup.infrastructure.plate.out.jpa.adapter;

import com.pragma.powerup.domain.category.model.CategoryModel;
import com.pragma.powerup.domain.plate.model.PlateModel;
import com.pragma.powerup.domain.plate.spi.IPlatePersistencePort;
import com.pragma.powerup.infrastructure.category.out.jpa.entity.CategoryEntity;
import com.pragma.powerup.infrastructure.category.out.jpa.repository.ICategoryRepository;
import com.pragma.powerup.infrastructure.client.IUserClient;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.plate.out.jpa.entity.PlateEntity;
import com.pragma.powerup.infrastructure.plate.out.jpa.mapper.IPlateEntityMapper;
import com.pragma.powerup.infrastructure.plate.out.jpa.repository.IPlateRepository;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pragma.powerup.infrastructure.utils.UserLogin.userLoginApplication;

@RequiredArgsConstructor
public class PlateJpaAdapter implements IPlatePersistencePort {
    private final HttpServletRequest request;
    private final IUserClient userClient;
    private final IRestaurantRepository restaurantRepository;
    private final IPlateRepository plateRepository;
    private final IPlateEntityMapper plateEntityMapper;
    private final ICategoryRepository categoryRepository;

    @Override
    public PlateModel savePlate(PlateModel plateModel) {
        if (!restaurantRepository.existsById(plateModel.getIdRestaurant())) {
            throw new NoDataFoundException();
        }
        if (!categoryRepository.existsById(plateModel.getIdCategory())) {
            throw new NoDataFoundException();
        }
        PlateEntity plateEntity = plateRepository.save(plateEntityMapper.toEntity(plateModel));
        return plateEntityMapper.toPlateModel(plateEntity);
    }

    @Override
    public PlateModel updatePlate(Long id, PlateModel plateModel) {
        Optional<PlateEntity> plateEntityFind = plateRepository.findById(id);
        if (plateEntityFind.isEmpty()) {
            throw new NoDataFoundException();
        }
        PlateEntity plateEntity = plateEntityFind.get();
        plateEntity.setPrice(plateModel.getPrice());
        plateEntity.setDescription(plateModel.getDescription());
        PlateModel plate = plateEntityMapper.toPlateModel(plateRepository.save(plateEntity));
        return plate;
    }

    @Override
    public PlateModel getPlate(Long id) {
        Optional<PlateEntity> plateEntityFind = plateRepository.findById(id);
        if (plateEntityFind.isEmpty()) {
            throw new NoDataFoundException();
        }
        return plateEntityMapper.toPlateModel(plateEntityFind.get());
    }

    @Override
    public Map<CategoryModel, List<PlateModel>> listPaginatedPlates(Long idRestaurant, Integer numberPage, Integer size) {

        List<PlateEntity> plates = plateRepository.findAllByIdRestaurant(idRestaurant, PageRequest.of(numberPage, size)).toList();
        if (plates.isEmpty()) {
            throw new NoDataFoundException();
        }
        Map<CategoryEntity, List<PlateEntity>> platesByCategory = plates.stream().collect(Collectors.groupingBy(
                PlateEntity::getCategory
        ));
        return plateEntityMapper.toPlatesByCategoryModel(platesByCategory);
    }

    @Override
    public PlateModel updateStatusPlate(Long id, Boolean status) {
        Optional<PlateEntity> plateEntityFind = plateRepository.findById(id);
        if (plateEntityFind.isEmpty()) {
            throw new NoDataFoundException();
        }
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(plateEntityFind.get().getIdRestaurant());
        userClient.validateOwner(request.getHeader("Authorization"), restaurantEntity.get().getIdOwner(), userLoginApplication());
        PlateEntity plateEntity = plateEntityFind.get();
        plateEntity.setActive(status);
        PlateModel plate = plateEntityMapper.toPlateModel(plateRepository.save(plateEntity));
        return plate;
    }


}
