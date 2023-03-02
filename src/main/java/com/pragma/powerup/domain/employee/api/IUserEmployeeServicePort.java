package com.pragma.powerup.domain.employee.api;

import com.pragma.powerup.domain.employee.model.UserEmployeeModel;

public interface IUserEmployeeServicePort {
    void saveEmployee(UserEmployeeModel userEmployeeModel);
}
