package com.pragma.powerup.application.employee.handler;

import com.pragma.powerup.application.employee.dto.request.UserEmployeeRequestDto;

public interface IUserEmployeeHandler {
    void saveEmployee(UserEmployeeRequestDto userEmployeeModel);
}
