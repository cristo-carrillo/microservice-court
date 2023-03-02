package com.pragma.powerup.infrastructure.restaurant.out.jpa.repository;

import com.pragma.powerup.infrastructure.restaurant.out.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    Optional<RestaurantEntity> findByNit(Long nit);
    Optional<RestaurantEntity> findByIdOwner(Long idOwner);
}
