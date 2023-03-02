package com.pragma.powerup.domain.restaurant.api;

import com.pragma.powerup.domain.restaurant.model.RestaurantModel;

import java.util.List;

public interface IRestaurantServicePort {

    void saveRestaurant(RestaurantModel restaurantModel);
    List<RestaurantModel> listPaginatedRestaurants(Integer numberPage, Integer size);
}
