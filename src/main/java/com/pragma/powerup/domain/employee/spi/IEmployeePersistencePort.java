package com.pragma.powerup.domain.employee.spi;

import com.pragma.powerup.domain.employee.model.EmployeeModel;

public interface IEmployeePersistencePort {
    void saveEmployee(EmployeeModel employee);
}
