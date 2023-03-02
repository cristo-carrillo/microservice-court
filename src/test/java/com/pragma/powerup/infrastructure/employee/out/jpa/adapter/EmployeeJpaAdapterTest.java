package com.pragma.powerup.infrastructure.employee.out.jpa.adapter;

import com.pragma.powerup.domain.employee.model.EmployeeModel;
import com.pragma.powerup.infrastructure.employee.out.jpa.entity.EmployeeEntity;
import com.pragma.powerup.infrastructure.employee.out.jpa.mapper.IEmployeeEntityMapper;
import com.pragma.powerup.infrastructure.employee.out.jpa.repository.IEmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.pragma.powerup.factory.FactoryEmployeeDataTest.getEmployeeEntity;
import static com.pragma.powerup.factory.FactoryEmployeeDataTest.getEmployeeModel;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EmployeeJpaAdapterTest {
    @InjectMocks
    EmployeeJpaAdapter employeeJpaAdapter;
    @Mock
    IEmployeeRepository employeeRepository;
    @Mock
    IEmployeeEntityMapper employeeEntityMapper;
    @Test
    void mustSaveEmployee() {
        //Given
        //Yo como usuario quiero guardar un empleado
        EmployeeEntity employeeEntity = getEmployeeEntity();
        EmployeeModel employeeModel = getEmployeeModel();

        //When
        //Le envio todos los datos correctamente
        when(employeeEntityMapper.toEntity(employeeModel)).thenReturn(employeeEntity);
        when(employeeRepository.save(employeeEntity)).thenReturn(new EmployeeEntity());

        //Then
        //El sistema guarda un nuevo empleado asociado a un restaurante
        employeeJpaAdapter.saveEmployee(employeeModel);
        verify(employeeRepository).save(employeeEntity);
    }

}