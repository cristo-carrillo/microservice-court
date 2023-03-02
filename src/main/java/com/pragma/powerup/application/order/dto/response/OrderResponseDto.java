package com.pragma.powerup.application.order.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Long idClient;
    private Date date;
    private String status;
    private Long idChef;
    private Long idRestaurant;
}
