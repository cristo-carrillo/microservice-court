package com.pragma.powerup.application.employee.handler.imp;

import com.pragma.powerup.application.employee.dto.request.UserEmployeeRequestDto;
import com.pragma.powerup.application.employee.handler.IUserEmployeeHandler;
import com.pragma.powerup.application.employee.mapper.IUserEmployeeRequestMapper;
import com.pragma.powerup.domain.employee.api.IUserEmployeeServicePort;
import com.pragma.powerup.domain.employee.model.UserEmployeeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserEmployeeHandler implements IUserEmployeeHandler {
    private final IUserEmployeeServicePort userEmployeeServicePort;
    private final IUserEmployeeRequestMapper userEmployeeRequestMapper;
    @Override
    public void saveEmployee(UserEmployeeRequestDto userEmployeeModelRequestDto) {
        UserEmployeeModel userEmployeeModel = userEmployeeRequestMapper.toUserEmployeeModel(userEmployeeModelRequestDto);
        userEmployeeServicePort.saveEmployee(userEmployeeModel);
    }
}
