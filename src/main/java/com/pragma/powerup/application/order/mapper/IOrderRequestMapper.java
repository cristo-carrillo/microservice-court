package com.pragma.powerup.application.order.mapper;

import com.pragma.powerup.application.order.dto.request.OrderRequestDto;
import com.pragma.powerup.domain.order.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderRequestMapper {
    OrderModel toOrder(OrderRequestDto orderRequestDto);
}
