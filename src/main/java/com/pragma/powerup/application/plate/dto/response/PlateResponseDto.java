package com.pragma.powerup.application.plate.dto.response;

import com.pragma.powerup.application.category.dto.response.CategoryResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlateResponseDto {

    private String nameDto;
    private String descriptionDto;
    private Long price;
    private Long idRestaurant;
    private String urlPicture;
    private CategoryResponseDto category;
}
