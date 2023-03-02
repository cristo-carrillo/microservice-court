package com.pragma.powerup.infrastructure.employee.out.jpa.adapter;

import com.pragma.powerup.domain.employee.model.EmployeeModel;
import com.pragma.powerup.domain.employee.spi.IEmployeePersistencePort;
import com.pragma.powerup.infrastructure.employee.out.jpa.entity.EmployeeEntity;
import com.pragma.powerup.infrastructure.employee.out.jpa.mapper.IEmployeeEntityMapper;
import com.pragma.powerup.infrastructure.employee.out.jpa.repository.IEmployeeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmployeeJpaAdapter implements IEmployeePersistencePort {
    private final IEmployeeRepository employeeRepository;
    private final IEmployeeEntityMapper employeeEntityMapper;
    @Override
    public void saveEmployee(EmployeeModel employee) {
        EmployeeEntity employeeEntity = employeeEntityMapper.toEntity(employee);
        employeeRepository.save(employeeEntity);
    }
}
