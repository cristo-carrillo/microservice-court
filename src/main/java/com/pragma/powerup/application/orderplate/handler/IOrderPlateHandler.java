package com.pragma.powerup.application.orderplate.handler;

import com.pragma.powerup.application.order.dto.request.OrderRequestDto;
import com.pragma.powerup.application.orderplate.dto.request.OrderPlatesRequestDto;

import java.util.List;

public interface IOrderPlateHandler {
    void saveOrder(List<OrderPlatesRequestDto> orderRequestDto);
}
