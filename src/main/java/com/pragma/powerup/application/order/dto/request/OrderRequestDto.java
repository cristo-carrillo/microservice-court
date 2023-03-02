package com.pragma.powerup.application.order.dto.request;

import com.pragma.powerup.application.orderplate.dto.request.OrderPlatesRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class OrderRequestDto {
    private Long idRestaurant;
    private List<OrderPlatesRequestDto> orderPlates;
}
