package com.pragma.powerup.infrastructure.orderplate.out.jpa.mapper;

import com.pragma.powerup.domain.orderplates.model.OrderPlatesModel;
import com.pragma.powerup.infrastructure.orderplate.out.jpa.entity.OrderPlateEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderPlateEntityMapper {

    @Mapping(source = "orderPlatesModel.idOrder", target = "orderPlatePK.idOrder")
    @Mapping(source = "orderPlatesModel.idPlate", target = "orderPlatePK.idPlate")
    @Mapping(source = "orderPlatesModel.quantity", target = "quantity")
    OrderPlateEntity toEntity(OrderPlatesModel orderPlatesModel);
    List<OrderPlateEntity> orderPlates(List<OrderPlatesModel> orderPlatesModels);
    @InheritInverseConfiguration
    List<OrderPlatesModel> orderPlatesModel(List<OrderPlateEntity> orderPlateEntities);
}
