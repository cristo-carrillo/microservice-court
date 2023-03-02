package com.pragma.powerup.application.order.handler.imp;

import com.pragma.powerup.application.order.dto.request.OrderRequestDto;
import com.pragma.powerup.application.order.dto.response.OrderResponseDto;
import com.pragma.powerup.application.order.handler.IOrderHandler;
import com.pragma.powerup.application.order.mapper.IOrderRequestMapper;
import com.pragma.powerup.application.order.mapper.IOrderResponseMapper;
import com.pragma.powerup.domain.order.api.IOrderServicePort;
import com.pragma.powerup.domain.order.model.OrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {
    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderResponseMapper orderResponseMapper;
    @Override
    public void saveOrder(OrderRequestDto orderRequestDto) {

        OrderModel orderModel = orderRequestMapper.toOrder(orderRequestDto);
        orderServicePort.saveOrder(orderModel);
    }

    @Override
    public List<OrderResponseDto> listPaginatedOrders(String status, Integer numberElementsPerPage, Integer size) {
        List<OrderModel> orderModelList = orderServicePort.listPaginatedOrders(status, numberElementsPerPage, size);
        return orderResponseMapper.toOderResponseList(orderModelList);
    }

    @Override
    public OrderResponseDto updateStatusPendingOrder(Long idOrder) {
        OrderModel orderModel = orderServicePort.updateStatusPendingOrder(idOrder);
        return orderResponseMapper.toOrderResponseDto(orderModel);
    }

    @Override
    public void sendMessageClientOrderReady(Long id, String message) {
        orderServicePort.sendMessageClientOrderReady(id, message);
    }

    @Override
    public void deliverOrderClient(Integer code, Long id) {
        orderServicePort.deliverOrderClient(code, id);
    }

    @Override
    public void cancelOrder(Long id) {
        orderServicePort.cancelOrder(id);
    }
}
