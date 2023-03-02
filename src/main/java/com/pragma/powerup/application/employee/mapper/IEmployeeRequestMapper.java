package com.pragma.powerup.application.employee.mapper;

import com.pragma.powerup.application.employee.dto.request.EmployeeRequestDto;
import com.pragma.powerup.domain.employee.model.EmployeeModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEmployeeRequestMapper {
    EmployeeModel toEmployee(EmployeeRequestDto employeeRequest);
}
