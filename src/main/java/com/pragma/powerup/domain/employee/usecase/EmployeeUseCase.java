package com.pragma.powerup.domain.employee.usecase;

import com.pragma.powerup.domain.employee.api.IEmployeeServicePort;
import com.pragma.powerup.domain.employee.model.EmployeeModel;
import com.pragma.powerup.domain.employee.spi.IEmployeePersistencePort;

public class EmployeeUseCase implements IEmployeeServicePort {

    private final IEmployeePersistencePort employeeServicePort;

    public EmployeeUseCase(IEmployeePersistencePort employeePersistencePort) {
        this.employeeServicePort = employeePersistencePort;
    }

    @Override
    public void saveEmployee(EmployeeModel employeeModel) {
        employeeServicePort.saveEmployee(employeeModel);
    }
}
