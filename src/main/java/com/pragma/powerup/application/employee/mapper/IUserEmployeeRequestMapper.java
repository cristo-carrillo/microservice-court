package com.pragma.powerup.application.employee.mapper;

import com.pragma.powerup.application.employee.dto.request.UserEmployeeRequestDto;
import com.pragma.powerup.domain.employee.model.UserEmployeeModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserEmployeeRequestMapper {
    UserEmployeeModel toUserEmployeeModel(UserEmployeeRequestDto userEmployeeRequestDto);
}
