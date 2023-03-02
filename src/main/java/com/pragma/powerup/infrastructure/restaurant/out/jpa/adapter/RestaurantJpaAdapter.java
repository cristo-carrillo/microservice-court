package com.pragma.powerup.infrastructure.restaurant.out.jpa.adapter;


import com.pragma.powerup.domain.restaurant.model.RestaurantModel;
import com.pragma.powerup.domain.restaurant.spi.IRestaurantPersistencePort;
import com.pragma.powerup.infrastructure.client.IUserClient;
import com.pragma.powerup.infrastructure.exception.AlreadyExistsException;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {
    private final HttpServletRequest request;
    private final IUserClient userClient;
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Override
    public RestaurantModel saveRestaurant(RestaurantModel restaurantModel) {
        userClient.validateRolUser(request.getHeader("Authorization"), restaurantModel.getIdOwner());
        if (restaurantRepository.findByNit(restaurantModel.getNit()).isPresent()) {
            throw new AlreadyExistsException("Restaurant " + restaurantModel.getNit());
        }

        RestaurantEntity restaurantEntity = restaurantRepository.save(restaurantEntityMapper.toEntity(restaurantModel));
        return restaurantEntityMapper.toRestaurantModel(restaurantEntity);
    }

    @Override
    public List<RestaurantModel> listPaginatedRestaurants(Integer numberElementsPerPage, Integer size) {
        Page<RestaurantEntity> page = restaurantRepository.findAll(
                PageRequest.of(numberElementsPerPage, size, Sort.by("name"))
        );
        List<RestaurantEntity> restaurants = page.getContent();

        return restaurantEntityMapper.toRestaurantsModelList(restaurants);
    }


}
