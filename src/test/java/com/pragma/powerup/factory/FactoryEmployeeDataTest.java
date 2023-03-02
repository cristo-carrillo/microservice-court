package com.pragma.powerup.factory;

import com.pragma.powerup.domain.employee.model.EmployeeModel;
import com.pragma.powerup.domain.employee.model.UserEmployeeModel;
import com.pragma.powerup.infrastructure.client.model.UserRequestDto;
import com.pragma.powerup.infrastructure.employee.out.jpa.entity.EmployeeEntity;
import com.pragma.powerup.infrastructure.employee.out.jpa.entity.RestaurantEmployeePK;

import java.util.List;

public class FactoryEmployeeDataTest {

    public static EmployeeEntity getEmployeeEntity() {
        return new EmployeeEntity(
                new RestaurantEmployeePK(1L, 2L)
        );
    }

    public static UserEmployeeModel getUserEmployeeModel() {
        return new UserEmployeeModel(
                "PEPE",
                "PEREZ",
                21254545L,
                "3209290211",
                "cjca@gmail.com",
                "ashsdkasdaks",
                1L
        );
    }

    public static UserRequestDto getUserRequestDto() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setName("PEPE");
        userRequestDto.setLastName("PEREZ");
        userRequestDto.setIdentityDocument(21254545L);
        userRequestDto.setPhone("3209290211");
        userRequestDto.setEmail("cjca@gmail.com");
        userRequestDto.setPassword("ashsdkasdaks");
        userRequestDto.setIdRol(1L);
        return userRequestDto;
    }

    public static EmployeeModel getEmployeeModel() {
        return new EmployeeModel(
                1L,
                2L
        );
    }
    public static List<EmployeeEntity> getListEmployees() {
        return List.of(
                new EmployeeEntity(new RestaurantEmployeePK(15L,1L)),
                new EmployeeEntity(new RestaurantEmployeePK(16L,1L)),
                new EmployeeEntity(new RestaurantEmployeePK(17L,1L)),
                new EmployeeEntity(new RestaurantEmployeePK(18L,2L)),
                new EmployeeEntity(new RestaurantEmployeePK(19L,2L)),
                new EmployeeEntity(new RestaurantEmployeePK(20L,2L)));
    }
}
