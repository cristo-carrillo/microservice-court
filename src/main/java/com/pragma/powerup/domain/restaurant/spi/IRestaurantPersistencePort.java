package com.pragma.powerup.domain.restaurant.spi;

import com.pragma.powerup.domain.restaurant.model.RestaurantModel;

import java.util.List;

public interface IRestaurantPersistencePort {

    RestaurantModel saveRestaurant(RestaurantModel restaurantModel);
    List<RestaurantModel> listPaginatedRestaurants(Integer numberElementsPerPage, Integer size);
}
