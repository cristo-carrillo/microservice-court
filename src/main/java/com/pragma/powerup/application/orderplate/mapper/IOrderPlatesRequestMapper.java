package com.pragma.powerup.application.orderplate.mapper;

import com.pragma.powerup.application.orderplate.dto.request.OrderPlatesRequestDto;
import com.pragma.powerup.domain.orderplates.model.OrderPlatesModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderPlatesRequestMapper {
    OrderPlatesModel toOrderPlates(OrderPlatesRequestDto orderPlatesRequestDto);
    List<OrderPlatesModel> toOrderPlates(List<OrderPlatesRequestDto> orderPlatesRequestDto);
}
