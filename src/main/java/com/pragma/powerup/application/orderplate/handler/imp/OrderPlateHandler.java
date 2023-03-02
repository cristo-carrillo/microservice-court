package com.pragma.powerup.application.orderplate.handler.imp;

import com.pragma.powerup.application.order.mapper.IOrderRequestMapper;
import com.pragma.powerup.application.orderplate.dto.request.OrderPlatesRequestDto;
import com.pragma.powerup.application.orderplate.handler.IOrderPlateHandler;
import com.pragma.powerup.application.orderplate.mapper.IOrderPlatesRequestMapper;
import com.pragma.powerup.domain.orderplates.api.IOrderPlatesServicePort;
import com.pragma.powerup.domain.orderplates.model.OrderPlatesModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class OrderPlateHandler implements IOrderPlateHandler {
    private final IOrderPlatesServicePort orderPlatesServicePort;
    private final IOrderPlatesRequestMapper orderRequestMapper;
    @Override
    public void saveOrder(List<OrderPlatesRequestDto> orderRequestDto) {
        List<OrderPlatesModel> orderPlatesModelList = orderRequestMapper.toOrderPlates(orderRequestDto);
        orderPlatesServicePort.saveOrderPlates(orderPlatesModelList);
    }
}
