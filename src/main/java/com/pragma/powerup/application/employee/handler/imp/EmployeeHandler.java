package com.pragma.powerup.application.employee.handler.imp;

import com.pragma.powerup.application.employee.dto.request.EmployeeRequestDto;
import com.pragma.powerup.application.employee.handler.IEmployeeHandler;
import com.pragma.powerup.application.employee.mapper.IEmployeeRequestMapper;
import com.pragma.powerup.domain.employee.api.IEmployeeServicePort;
import com.pragma.powerup.domain.employee.model.EmployeeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeHandler implements IEmployeeHandler {
    private final IEmployeeServicePort employeeServicePort;
    private final IEmployeeRequestMapper employeeRequestMapper;
    @Override
    public void saveEmploy(EmployeeRequestDto EmployeeRequestDto) {
        EmployeeModel employeeModel = employeeRequestMapper.toEmployee(EmployeeRequestDto);
        employeeServicePort.saveEmployee(employeeModel);
    }
}
