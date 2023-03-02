package com.pragma.powerup.application.restaurant.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantRequestDto {

    private String name;
    private String address;
    private Long idOwner;
    private String phone;
    private String urlLogo;
    private Long nit;
}
