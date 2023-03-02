package com.pragma.powerup.domain.employee.api;

import com.pragma.powerup.domain.employee.model.EmployeeModel;

public interface IEmployeeServicePort {

    void saveEmployee(EmployeeModel employeeModel);
}
