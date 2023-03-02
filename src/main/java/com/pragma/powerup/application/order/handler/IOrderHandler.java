package com.pragma.powerup.application.order.handler;

import com.pragma.powerup.application.order.dto.request.OrderRequestDto;
import com.pragma.powerup.application.order.dto.response.OrderResponseDto;

import java.util.List;

public interface IOrderHandler {
    void saveOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> listPaginatedOrders(String status, Integer numberElementsPerPage, Integer size);

    OrderResponseDto updateStatusPendingOrder(Long idOrder);

    void sendMessageClientOrderReady(Long id, String message);

    void deliverOrderClient(Integer code, Long id);

    void cancelOrder(Long id);
}
