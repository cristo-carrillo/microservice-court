package com.pragma.powerup.infrastructure.employee.out.jpa.mapper;

import com.pragma.powerup.domain.employee.model.UserEmployeeModel;
import com.pragma.powerup.infrastructure.client.model.UserRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserEmployeeClientMapper {
    UserRequestDto toUserDto(UserEmployeeModel userEmployeeModel);
}
