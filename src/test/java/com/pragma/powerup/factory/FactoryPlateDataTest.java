package com.pragma.powerup.factory;

import com.pragma.powerup.domain.category.model.CategoryModel;
import com.pragma.powerup.domain.plate.model.PlateModel;
import com.pragma.powerup.infrastructure.category.out.jpa.entity.CategoryEntity;
import com.pragma.powerup.infrastructure.plate.out.jpa.entity.PlateEntity;

import java.util.List;
import java.util.Map;

public class FactoryPlateDataTest {

    public static PlateModel getPlateModel() {
        PlateModel plateModel = new PlateModel();
        plateModel.setName("name");
        plateModel.setIdCategory(1L);
        plateModel.setDescription("description");
        plateModel.setPrice(5454L);
        plateModel.setIdRestaurant(2L);
        plateModel.setUrlPicture("http");
        plateModel.setActive(true);
        return plateModel;
    }

    public static PlateEntity getPlateEntity() {
        PlateEntity plateEntity = new PlateEntity();
        plateEntity.setId(1L);
        plateEntity.setName("name");
        plateEntity.setIdCategory(1L);
        plateEntity.setDescription("description");
        plateEntity.setPrice(5454L);
        plateEntity.setIdRestaurant(2L);
        plateEntity.setUrlPicture("http");
        plateEntity.setActive(true);
        return plateEntity;
    }

    public static List<PlateEntity> getPlateEntities() {
        return List.of(
                new PlateEntity(1L, "plate1", 1L, "plate 1", 2000L, 1L, "http", true,
                        new CategoryEntity(
                                1L, "category1", "category 1", List.of(
                                new PlateEntity(1L, "plate1", 1L, "plate 1", 2000L, 1L, "http", true, null),
                                new PlateEntity(2L, "plate2", 1L, "plate 2", 2000L, 1L, "http", true, null)
                            )
                        )
                ),
                new PlateEntity(2L, "plate2", 1L, "plate 2", 2000L, 1L, "http", true,
                        new CategoryEntity(
                                1L, "category1", "category 1", List.of(
                                new PlateEntity(1L, "plate1", 1L, "plate 1", 2000L, 1L, "http", true, null),
                                new PlateEntity(2L, "plate2", 1L, "plate 2", 2000L, 1L, "http", true, null)
                        )
                        )
                )

        );
    }

    public static Map<CategoryModel, List<PlateModel>> getPlatesByCategory(){
        return Map.of(
                new CategoryModel(1L, "category1", "category 1", List.of(
                        new PlateModel(1L, "plate1", 1L, "plate 1", 2000L, 1L, "http", true, null),
                        new PlateModel(2L, "plate2", 1L, "plate 2", 2000L, 1L, "http", true, null)
                )), List.of(new PlateModel(1L, "plate1", 1L, "plate 1", 2000L, 1L, "http", true, null),
                        new PlateModel(2L, "plate2", 1L, "plate 2", 2000L, 1L, "http", true, null))
        );
    }
}
