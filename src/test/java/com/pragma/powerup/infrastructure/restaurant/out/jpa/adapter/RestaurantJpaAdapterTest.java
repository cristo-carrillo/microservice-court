package com.pragma.powerup.infrastructure.restaurant.out.jpa.adapter;

import com.pragma.powerup.domain.restaurant.model.RestaurantModel;
import com.pragma.powerup.infrastructure.client.IUserClient;
import com.pragma.powerup.infrastructure.exception.AlreadyExistsException;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static com.pragma.powerup.factory.FactoryRestaurantDataTest.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class RestaurantJpaAdapterTest {
    @InjectMocks
    RestaurantJpaAdapter restaurantJpaAdapter;
    @Mock
    IUserClient userClient;
    @Mock
    IRestaurantRepository restaurantRepository;
    @Mock
    IRestaurantEntityMapper restaurantEntityMapper;
    @Mock
    HttpServletRequest request;

    @Test
    void mustSaveRestaurant() {
        //Given
        //yo como usuario quiero guardar un restaurante
        RestaurantEntity restaurantEntity = getRestaurantEntity();
        RestaurantModel restaurantModel = getRestaurantModel();
        RestaurantEntity restaurantEntityNew = new RestaurantEntity();
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        //When
        //Le envio todos los datos correctamente
        when(request.getHeader("Authorization")).thenReturn(token);
        when(userClient.validateRolUser(token, restaurantModel.getIdOwner())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(restaurantRepository.findByNit(anyLong())).thenReturn(Optional.empty());
        when(restaurantEntityMapper.toEntity(restaurantModel)).thenReturn(restaurantEntity);
        when(restaurantRepository.save(restaurantEntity)).thenReturn(restaurantEntityNew);
        when(restaurantEntityMapper.toRestaurantModel(restaurantEntityNew)).thenReturn(new RestaurantModel());

        //Then
        //El sistema me guarda el nuevo restaurante en la base de datos
        assertNotNull(restaurantJpaAdapter.saveRestaurant(restaurantModel));

    }

    @Test
    void throwAlreadyExistsExceptionWhenAttemptSaveRestaurant() {
        //Given
        // Yo como usuario quiero guardar un usuarioq ue ya existe
        RestaurantModel restaurantModel = getRestaurantModel();
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        //When
        //Le vio un restaurante que ya existe
        when(request.getHeader("Authorization")).thenReturn(token);
        when(userClient.validateRolUser(token, restaurantModel.getIdOwner())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(restaurantRepository.findByNit(anyLong())).thenReturn(Optional.of(getRestaurantEntity()));

        //Then
        //El sistema retorna una exception del tipo AlreadyExistsException
        assertThrows(AlreadyExistsException.class, () -> restaurantJpaAdapter.saveRestaurant(restaurantModel));
    }

    @Test
    void mustPageAvailableRestaurants(){
        //Given
        //Yo como usuario quiero obtener los restaurantes paginados ordenados por nombre
        List<RestaurantEntity> restaurantEntities = listRestaurantsEntity();
        List<RestaurantModel> restaurantModels = listRestaurantsModel();

        //When
        //Le envio todos los datos correctamente
        when(restaurantRepository.findAll(any(PageRequest.class))).thenReturn(
                new PageImpl<>(restaurantEntities)
        );
        when(restaurantEntityMapper.toRestaurantsModelList(restaurantEntities)).thenReturn(restaurantModels);

        //Then
        //El sistema me obtiene los restaurantes paginados ordenados por nombre
        assertEquals(restaurantModels, restaurantJpaAdapter.listPaginatedRestaurants(0, 5));
    }
}