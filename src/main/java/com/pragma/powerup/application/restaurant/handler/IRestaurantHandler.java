package com.pragma.powerup.application.restaurant.handler;

import com.pragma.powerup.application.restaurant.dto.request.RestaurantRequestDto;
import com.pragma.powerup.application.restaurant.dto.response.RestaurantListResponseDto;

import java.util.List;

public interface IRestaurantHandler {
    void saveRestaurant(RestaurantRequestDto restaurantResponseDto);
    List<RestaurantListResponseDto> listPaginatedRestaurants(Integer numberElementsPerPage, Integer size);
}
