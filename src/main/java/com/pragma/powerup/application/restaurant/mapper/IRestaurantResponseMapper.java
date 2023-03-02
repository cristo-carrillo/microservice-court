package com.pragma.powerup.application.restaurant.mapper;

import com.pragma.powerup.application.restaurant.dto.response.RestaurantListResponseDto;
import com.pragma.powerup.application.restaurant.dto.response.RestaurantResponseDto;
import com.pragma.powerup.domain.restaurant.model.RestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantResponseMapper {
    RestaurantResponseDto toResponse(RestaurantModel restaurantModel);
    List<RestaurantListResponseDto> toResponseList(List<RestaurantModel> restaurantModel);
}
