package com.pragma.powerup.application.restaurant.mapper;

import com.pragma.powerup.application.restaurant.dto.request.RestaurantRequestDto;
import com.pragma.powerup.domain.restaurant.model.RestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantRequestMapper {

    RestaurantModel toRestaurant(RestaurantRequestDto restaurantRequestDto);
}
