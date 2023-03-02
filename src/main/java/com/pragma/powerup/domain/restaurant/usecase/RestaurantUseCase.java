package com.pragma.powerup.domain.restaurant.usecase;

import com.pragma.powerup.domain.restaurant.api.IRestaurantServicePort;
import com.pragma.powerup.domain.restaurant.model.RestaurantModel;
import com.pragma.powerup.domain.restaurant.spi.IRestaurantPersistencePort;

import java.util.List;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort){
        this.restaurantPersistencePort = restaurantPersistencePort;
    }
    @Override
    public void saveRestaurant(RestaurantModel restaurantModel) {
        this.restaurantPersistencePort.saveRestaurant(restaurantModel);
    }

    @Override
    public List<RestaurantModel> listPaginatedRestaurants(Integer numberElementsPerPage, Integer size) {
        return restaurantPersistencePort.listPaginatedRestaurants(numberElementsPerPage, size);
    }
}
