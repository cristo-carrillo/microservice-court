package com.pragma.powerup.infrastructure.employee.out.jpa.adapter;

import com.pragma.powerup.domain.employee.model.UserEmployeeModel;
import com.pragma.powerup.infrastructure.client.IUserClient;
import com.pragma.powerup.infrastructure.client.model.UserRequestDto;
import com.pragma.powerup.infrastructure.employee.out.jpa.entity.EmployeeEntity;
import com.pragma.powerup.infrastructure.employee.out.jpa.mapper.IUserEmployeeClientMapper;
import com.pragma.powerup.infrastructure.employee.out.jpa.repository.IEmployeeRepository;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static com.pragma.powerup.factory.FactoryEmployeeDataTest.*;
import static com.pragma.powerup.factory.FactoryRestaurantDataTest.getRestaurantEntity;
import static com.pragma.powerup.infrastructure.utils.UserLogin.userLoginApplication;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserEmployeeJpaAdapterTest {
    @InjectMocks
    UserEmployeeJpaAdapter userEmployeeJpaAdapter;
    @Mock
    HttpServletRequest request;
    @Mock
    IUserClient userClient;
    @Mock
    IUserEmployeeClientMapper userEmployeeClientMapper;
    @Mock
    IRestaurantRepository restaurantRepository;
    @Mock
    IEmployeeRepository employeeRepository;

    @Test
    void mustSaveEmployee() {
        //Given
        //Yo como usuario propietario quiero guardar un usuario empleado
        UserEmployeeModel userEmployeeModel = getUserEmployeeModel();
        Long idUserOwner = 1L;
        RestaurantEntity restaurantEntity = getRestaurantEntity();
        Long idEmployee = 2L;
        Authentication authentication = mock(Authentication.class);
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user = mock(UserDetails.class);
        String expectedUsername = "owner@gmail.com";
        UserRequestDto userRequestDto = getUserRequestDto();


        //When
        //Le envio los datos correctamente
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserOwner, HttpStatus.OK));
        when(restaurantRepository.findByIdOwner(idUserOwner)).thenReturn(Optional.of(restaurantEntity));
        when(request.getHeader("Authorization")).thenReturn(token);
        when(userEmployeeClientMapper.toUserDto(userEmployeeModel)).thenReturn(userRequestDto);
        when(userClient.saveUserEmployee(token, userRequestDto)).thenReturn(new ResponseEntity<>(idEmployee, HttpStatus.CREATED));
        when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(new EmployeeEntity());

        userEmployeeJpaAdapter.saveEmployee(userEmployeeModel);

        //Then
        //Verifico que el empleado se ha agregado coreectamente
        verify(userClient).getIdUser(eq(token), any());
        verify(restaurantRepository).findByIdOwner(eq(idUserOwner));
        verify(userClient).saveUserEmployee(eq(token), any());

    }
    @Test
    void throwNoDataFoundExceptionWhenAttemptSaveEmployee() {
        //Given
        //Yo como usuario propietario quiero guardar un usuario empleado pero no tengo un restaurante asociado
        UserEmployeeModel userEmployeeModel = getUserEmployeeModel();
        Long idUserOwner = 5L;
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user = mock(UserDetails.class);
        String expectedUsername = "pepe@gmail.com";

        //When
        //el usuario logueado no tiene restaurantes asociados
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserOwner, HttpStatus.OK));
        when(restaurantRepository.findByIdOwner(idUserOwner)).thenReturn(Optional.empty());

        //Then
        //El sistema retorna una exception del tipo NoDataFoundException
        assertThrows(NoDataFoundException.class, () -> userEmployeeJpaAdapter.saveEmployee(userEmployeeModel));
    }

}