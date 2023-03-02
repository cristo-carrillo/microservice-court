package com.pragma.powerup.infrastructure.employee.out.jpa.mapper;

import com.pragma.powerup.domain.employee.model.EmployeeModel;
import com.pragma.powerup.infrastructure.employee.out.jpa.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IEmployeeEntityMapper {

    @Mapping(source = "employee.idRestaurant", target = "restaurantEmployeePK.idRestaurant")
    @Mapping(source = "employee.idPeople", target = "restaurantEmployeePK.idPeople")
    EmployeeEntity toEntity(EmployeeModel employee);
}
