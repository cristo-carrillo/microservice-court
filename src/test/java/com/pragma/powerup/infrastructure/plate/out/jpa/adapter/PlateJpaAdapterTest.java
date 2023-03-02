package com.pragma.powerup.infrastructure.plate.out.jpa.adapter;

import com.pragma.powerup.domain.category.model.CategoryModel;
import com.pragma.powerup.domain.plate.model.PlateModel;
import com.pragma.powerup.infrastructure.category.out.jpa.repository.ICategoryRepository;
import com.pragma.powerup.infrastructure.client.IUserClient;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.plate.out.jpa.entity.PlateEntity;
import com.pragma.powerup.infrastructure.plate.out.jpa.mapper.IPlateEntityMapper;
import com.pragma.powerup.infrastructure.plate.out.jpa.repository.IPlateRepository;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.pragma.powerup.factory.FactoryPlateDataTest.*;
import static com.pragma.powerup.factory.FactoryRestaurantDataTest.getRestaurantEntity;
import static com.pragma.powerup.infrastructure.utils.UserLogin.userLoginApplication;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PlateJpaAdapterTest {
    @InjectMocks
    PlateJpaAdapter plateJpaAdapter;
    @Mock
    IPlateRepository plateRepository;
    @Mock
    IPlateEntityMapper plateEntityMapper;
    @Mock
    IUserClient userClient;
    @Mock
    HttpServletRequest request;
    @Mock
    IRestaurantRepository restaurantRepository;
    @Mock
    ICategoryRepository categoryRepository;

    @Test
    void mustSavePlate() {
        //Given
        //Yo como usuario quiero guardar un plato
        PlateModel plateModel = getPlateModel();
        PlateEntity plateEntity = getPlateEntity();

        //When
        //Le envio todos los datos correctamente
        when(restaurantRepository.existsById(plateModel.getIdRestaurant())).thenReturn(true);
        when(categoryRepository.existsById(plateModel.getIdCategory())).thenReturn(true);
        when(plateEntityMapper.toEntity(plateModel)).thenReturn(plateEntity);
        when(plateRepository.save(plateEntity)).thenReturn(new PlateEntity());
        when(plateEntityMapper.toPlateModel(any(PlateEntity.class))).thenReturn(new PlateModel());

        //Then
        //El sistema me guarda el nuevo plato en la base de datos
        assertNotNull(plateJpaAdapter.savePlate(plateModel));

    }

    @Test
    void throwNoDataFoundExceptionWhenAttemptSavePlateAndRestaurantNotExist() {
        //Given
        //Yo como usuario quiero guardar un plato asociandole un restaurante que no exista
        PlateModel plateModel = getPlateModel();

        //When
        //Le envio un id de restaurante que no existe
        when(restaurantRepository.existsById(plateModel.getIdRestaurant())).thenReturn(false);
        //Then
        //El sistema me retorna una exception del tipo NoDataFoundException
        assertThrows(
                NoDataFoundException.class, () -> plateJpaAdapter.savePlate(plateModel)
        );
    }

    @Test
    void throwNoDataFoundExceptionWhenAttemptSavePlateAndNoCategoryNotExist() {
        //Given
        //Yo como usuario quiero guardar un plato asociandole un categoria que no exista
        PlateModel plateModel = getPlateModel();

        //When
        //Le envio un id de categoria que no existe
        when(restaurantRepository.existsById(plateModel.getIdRestaurant())).thenReturn(true);
        when(categoryRepository.existsById(plateModel.getIdRestaurant())).thenReturn(false);

        //Then
        //El sistema me retorna una exception del tipo NoDataFoundException
        assertThrows(
                NoDataFoundException.class, () -> plateJpaAdapter.savePlate(plateModel)
        );
    }

    @Test
    void mustUpdatePlate() {
        //Given
        //Yo como usuario quiero actualizar un plato
        PlateModel plateModel = getPlateModel();
        PlateEntity plateEntity = getPlateEntity();

        //When
        //Le envio todos los datos correctamente
        when(plateRepository.findById(anyLong())).thenReturn(Optional.of(plateEntity));
        when(plateRepository.save(plateEntity)).thenReturn(new PlateEntity());
        when(plateEntityMapper.toPlateModel(any(PlateEntity.class))).thenReturn(new PlateModel());


        //Then
        //El sistema me actualiza el plato en la base de datos
        assertNotNull(plateJpaAdapter.updatePlate(anyLong(), plateModel));
    }

    @Test
    void throwNoDataFoundExceptionWhenAttemptUpdatePlate() {
        //Given
        //Yo como usuario quiero actualizar un plato que no existe
        PlateModel plateModel = getPlateModel();

        //When
        //Le envio un id de restaurante que no existe
        when(plateRepository.findById(anyLong())).thenReturn(Optional.empty());
        //Then
        //El sistema me retorna una exception del tipo NoDataFoundException
        assertThrows(
                NoDataFoundException.class, () -> plateJpaAdapter.updatePlate(anyLong(), plateModel)
        );
    }

    @Test
    void mustGetPlate() {
        //Given
        //Yo como usuario quiero obtener un plato
        PlateModel plateModel = getPlateModel();
        PlateEntity plateEntity = getPlateEntity();

        //When
        //Le envio todos los datos correctamente
        when(plateRepository.findById(anyLong())).thenReturn(Optional.of(plateEntity));
        when(plateEntityMapper.toPlateModel(plateEntity)).thenReturn(plateModel);

        //Then
        //El sistema me obtiene el plato en la base de datos
        assertNotNull(plateJpaAdapter.getPlate(anyLong()));
    }

    @Test
    void throwNoDataFoundExceptionWhenPlateNotExists() {

        //When
        //Le envio un id de restaurante que no existe
        when(plateRepository.findById(anyLong())).thenReturn(Optional.empty());
        //Then
        //El sistema me retorna una exception del tipo NoDataFoundException
        assertThrows(
                NoDataFoundException.class, () -> plateJpaAdapter.getPlate(anyLong())
        );
    }

    @Test
    void mustUpdateStatusPlate() {
        //Given
        //Yo como usuario quiero actualizar el estado de un plato
        PlateModel plateModel = getPlateModel();
        PlateEntity plateEntity = getPlateEntity();
        RestaurantEntity restaurantEntity = getRestaurantEntity();
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        String rolLogin = "ROLE_OWNER";
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user = mock(UserDetails.class);

        //When
        //Le envio todos los datos correctamente
        when(plateRepository.findById(anyLong())).thenReturn(Optional.of(plateEntity));
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantEntity));
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(rolLogin);
        when(userLoginApplication()).thenReturn(rolLogin);
        when(userClient.validateOwner(token, restaurantEntity.getIdOwner(), rolLogin)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(plateRepository.save(plateEntity)).thenReturn(new PlateEntity());
        when(plateEntityMapper.toPlateModel(any(PlateEntity.class))).thenReturn(new PlateModel());
        //Then
        //El sistema me actualiza el plato en la base de datos
        assertNotNull(plateJpaAdapter.updateStatusPlate(1L, false));
    }

    @Test
    void throwNoDataFoundExceptionWhenAttemptUpdateStatusPlateAndNotExist() {
        //Given
        //Yo como usuario quiero actualizar el estado de un plato que no existe


        //When
        //Le envio el id de un plato que no existe
        when(plateRepository.findById(anyLong())).thenReturn(Optional.empty());
        //Then
        //El sistema me retorna una exception del tipo NoDataFoundException
        assertThrows(
                NoDataFoundException.class, () -> plateJpaAdapter.updateStatusPlate(anyLong(), false)
        );

    }

    @Test
    void mustListPaginatedPlates() {
        //Given
        //Yo como cliente de la plazoleta quiero obtener los platos paginados por restaurante
        Long idRestaurant = 1L;
        Integer numberPage = 0;
        Integer size = 2;
        List<PlateEntity> plates = getPlateEntities();
        Map<CategoryModel, List<PlateModel>> platesByCategory = getPlatesByCategory();

        //When
        //Le envio todos los datos correctamente
        when(plateRepository.findAllByIdRestaurant(idRestaurant, PageRequest.of(
                numberPage,
                size
        ))).thenReturn(new PageImpl<>(plates));
        when(plateEntityMapper.toPlatesByCategoryModel(any())).thenReturn(platesByCategory);

        //Then
        //El sistema me obtiene los platos paginados en la base de datos
        assertEquals(platesByCategory, plateJpaAdapter.listPaginatedPlates(idRestaurant, numberPage, size));
    }
    @Test
    void throwNoDataFoundExceptionWhenAttemptListPaginatedPlates() {
        //Given
        //Yo como cliente de la plazoleta quiero obtener los platos paginados por restaurante, pero el restaurante no tiene
        //platos asignados
        Long idRestaurant = 2L;
        Integer numberPage = 0;
        Integer size = 2;

        //When
        //Le envio el id de un restaurante que no tiene platos asociados
        when(plateRepository.findAllByIdRestaurant(idRestaurant, PageRequest.of(
                numberPage,
                size
        ))).thenReturn(new PageImpl<>(new ArrayList<>()));
        //Then
        //El sistema me retorna una exception del tipo NoDataFoundException
        assertThrows(
                NoDataFoundException.class, () -> plateJpaAdapter.listPaginatedPlates(idRestaurant, numberPage, size)
        );
    }


}