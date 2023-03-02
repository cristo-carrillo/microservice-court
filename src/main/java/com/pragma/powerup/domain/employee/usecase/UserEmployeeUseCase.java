package com.pragma.powerup.domain.employee.usecase;

import com.pragma.powerup.domain.employee.api.IUserEmployeeServicePort;
import com.pragma.powerup.domain.employee.model.UserEmployeeModel;
import com.pragma.powerup.domain.employee.spi.IUserEmployeePersistencePort;

public class UserEmployeeUseCase implements IUserEmployeeServicePort {
    private final IUserEmployeePersistencePort userEmployeePersistencePort;

    public UserEmployeeUseCase(IUserEmployeePersistencePort userEmployeePersistencePort) {
        this.userEmployeePersistencePort = userEmployeePersistencePort;
    }

    @Override
    public void saveEmployee(UserEmployeeModel userEmployeeModel) {
         userEmployeePersistencePort.saveEmployee(userEmployeeModel);
    }
}
