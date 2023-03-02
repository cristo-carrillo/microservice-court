package com.pragma.powerup.application.plate.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlateRequestDto {
    private String name;
    private Long idCategory;
    private String description;
    private Long price;
    private Long idRestaurant;
    private String urlPicture;

}
