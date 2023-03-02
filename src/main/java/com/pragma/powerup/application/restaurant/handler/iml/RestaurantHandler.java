package com.pragma.powerup.application.restaurant.handler.iml;

import com.pragma.powerup.application.restaurant.dto.request.RestaurantRequestDto;
import com.pragma.powerup.application.restaurant.dto.response.RestaurantListResponseDto;
import com.pragma.powerup.application.restaurant.dto.response.RestaurantResponseDto;
import com.pragma.powerup.application.restaurant.handler.IRestaurantHandler;
import com.pragma.powerup.application.restaurant.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.application.restaurant.mapper.IRestaurantResponseMapper;
import com.pragma.powerup.domain.restaurant.api.IRestaurantServicePort;
import com.pragma.powerup.domain.restaurant.model.RestaurantModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;


    @Override
    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
        RestaurantModel restaurantModel = restaurantRequestMapper.toRestaurant(restaurantRequestDto);
        restaurantServicePort.saveRestaurant(restaurantModel);
    }

    @Override
    public List<RestaurantListResponseDto> listPaginatedRestaurants(Integer numberElementsPerPage, Integer size) {
        List<RestaurantModel> restaurantModels = restaurantServicePort.listPaginatedRestaurants(numberElementsPerPage, size);
        return restaurantResponseMapper.toResponseList(restaurantModels);
    }

}
