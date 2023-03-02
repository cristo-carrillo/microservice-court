package com.pragma.powerup.domain.employee.spi;

import com.pragma.powerup.domain.employee.model.UserEmployeeModel;

public interface IUserEmployeePersistencePort {

    void saveEmployee(UserEmployeeModel userEmployeeModel);
}
