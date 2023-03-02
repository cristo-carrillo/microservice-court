package com.pragma.powerup.factory;

import com.pragma.powerup.domain.restaurant.model.RestaurantModel;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.entity.RestaurantEntity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactoryRestaurantDataTest {

    public static RestaurantEntity getRestaurantEntity(){
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setName("Test");
        restaurantEntity.setAddress("address");
        restaurantEntity.setIdOwner(1L);
        restaurantEntity.setPhone("3209290211");
        restaurantEntity.setUrlLogo("http://");
        restaurantEntity.setNit(165456L);
        return restaurantEntity;
    }

    public static RestaurantModel getRestaurantModel(){
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setName("Test");
        restaurantModel.setAddress("address");
        restaurantModel.setIdOwner(1L);
        restaurantModel.setPhone("3209290211");
        restaurantModel.setUrlLogo("http://");
        restaurantModel.setNit(165456L);
        return restaurantModel;
    }

    public static List<RestaurantEntity> listRestaurantsEntity(){
        return Arrays.asList(
                new RestaurantEntity(1L, "Test", "Address", 1l, "3209290211", "http://", 22233522L),
                new RestaurantEntity(2L, "Test2", "Address2", 2l, "3209290212", "http://", 22233562L),
                new RestaurantEntity(3L, "Test3", "Address3", 3l, "3209290213", "http://", 22233572L),
                new RestaurantEntity(4L, "Test4", "Address4", 4l, "3209290214", "http://", 22233571L),
                new RestaurantEntity(5L, "Test5", "Address5", 5l, "3209290215", "http://", 22233567L)

        );
    }

    public static List<RestaurantModel> listRestaurantsModel(){
        return Arrays.asList(
                new RestaurantModel(1L, "Test", "Address", 1l, "3209290211", "http://", 22233522L),
                new RestaurantModel(2L, "Test2", "Address2", 2l, "3209290212", "http://", 22233562L),
                new RestaurantModel(3L, "Test3", "Address3", 3l, "3209290213", "http://", 22233572L),
                new RestaurantModel(4L, "Test4", "Address4", 4l, "3209290214", "http://", 22233571L),
                new RestaurantModel(5L, "Test5", "Address5", 5l, "3209290215", "http://", 22233567L)
        );
    }
}
