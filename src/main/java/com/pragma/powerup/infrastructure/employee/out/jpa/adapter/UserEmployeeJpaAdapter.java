package com.pragma.powerup.infrastructure.employee.out.jpa.adapter;

import com.pragma.powerup.domain.employee.model.UserEmployeeModel;
import com.pragma.powerup.domain.employee.spi.IUserEmployeePersistencePort;
import com.pragma.powerup.infrastructure.client.IUserClient;
import com.pragma.powerup.infrastructure.employee.out.jpa.entity.EmployeeEntity;
import com.pragma.powerup.infrastructure.employee.out.jpa.entity.RestaurantEmployeePK;
import com.pragma.powerup.infrastructure.employee.out.jpa.mapper.IUserEmployeeClientMapper;
import com.pragma.powerup.infrastructure.employee.out.jpa.repository.IEmployeeRepository;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.pragma.powerup.infrastructure.utils.UserLogin.userLoginApplication;

@RequiredArgsConstructor
public class UserEmployeeJpaAdapter implements IUserEmployeePersistencePort {
    private final HttpServletRequest request;
    private final IUserClient userClient;
    private final IUserEmployeeClientMapper userEmployeeClientMapper;
    private final IRestaurantRepository restaurantRepository;
    private final IEmployeeRepository employeeRepository;

    @Override
    public void saveEmployee(UserEmployeeModel userEmployeeModel) {
        Long idUserOwner = userClient.getIdUser(request.getHeader("Authorization"), userLoginApplication()).getBody();
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findByIdOwner(idUserOwner);
        if(restaurantEntity.isEmpty()){
            throw new NoDataFoundException();
        }
        Long idEmployee = userClient.saveUserEmployee(request.getHeader("Authorization"),
                userEmployeeClientMapper.toUserDto(userEmployeeModel)).getBody();
        EmployeeEntity employeeRequestDto = new EmployeeEntity(new RestaurantEmployeePK(restaurantEntity.get().getId(), idEmployee));
        employeeRepository.save(employeeRequestDto);
    }
}
