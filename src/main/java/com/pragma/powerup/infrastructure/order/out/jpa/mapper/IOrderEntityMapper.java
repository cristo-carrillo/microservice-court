package com.pragma.powerup.infrastructure.order.out.jpa.mapper;

import com.pragma.powerup.domain.order.model.OrderModel;
import com.pragma.powerup.infrastructure.order.out.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderEntityMapper {
    OrderModel toOrderModel(OrderEntity orderEntity);
    OrderEntity toOderEntity(OrderModel orderModel);
    List<OrderModel> toOrderList(List<OrderEntity> order);
}
