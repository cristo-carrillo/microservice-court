package com.pragma.powerup.infrastructure.order.out.jpa.adapter;

import com.pragma.powerup.domain.order.model.MessageModel;
import com.pragma.powerup.domain.order.model.OrderModel;
import com.pragma.powerup.infrastructure.client.IMessagingClient;
import com.pragma.powerup.infrastructure.client.IUserClient;
import com.pragma.powerup.infrastructure.client.model.User;
import com.pragma.powerup.infrastructure.client.model.messaging.IMessageMapper;
import com.pragma.powerup.infrastructure.client.model.messaging.Messaging;
import com.pragma.powerup.infrastructure.employee.out.jpa.entity.EmployeeEntity;
import com.pragma.powerup.infrastructure.employee.out.jpa.repository.IEmployeeRepository;
import com.pragma.powerup.infrastructure.exception.*;
import com.pragma.powerup.infrastructure.order.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.order.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup.infrastructure.order.out.jpa.repository.IOrderRepository;
import com.pragma.powerup.infrastructure.orderplate.out.jpa.entity.OrderPlateEntity;
import com.pragma.powerup.infrastructure.orderplate.out.jpa.mapper.IOrderPlateEntityMapper;
import com.pragma.powerup.infrastructure.orderplate.out.jpa.repository.IOrderPlateRepository;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
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
import java.util.Optional;

import static com.pragma.powerup.factory.FactoryEmployeeDataTest.*;
import static com.pragma.powerup.factory.FactoryMessagingDataTest.messageModel;
import static com.pragma.powerup.factory.FactoryMessagingDataTest.messaging;
import static com.pragma.powerup.factory.FactoryOrderDataTest.*;
import static com.pragma.powerup.factory.FactoryUserDataTest.getUser;
import static com.pragma.powerup.infrastructure.utils.UserLogin.userLoginApplication;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class OrderJpaAdapterTest {
    @InjectMocks
    OrderJpaAdapter orderJpaAdapter;
    @Mock
    HttpServletRequest request;
    @Mock
    IUserClient userClient;
    @Mock
    IOrderRepository orderRepository;
    @Mock
    IOrderEntityMapper orderEntityMapper;
    @Mock
    IRestaurantRepository restaurantRepository;
    @Mock
    IOrderPlateRepository orderPlateRepository;
    @Mock
    IEmployeeRepository employeeRepository;
    @Mock
    IOrderPlateEntityMapper orderPlateEntityMapper;
    @Mock
    IMessagingClient messagingClient;
    @Mock
    IMessageMapper messageMapper;

    @Test
    void mustSaveOrder() {
        //GIVEN
        //Yo como cliente de la plazoleta quiero agregar pedidos
        OrderModel order = orderModel();
        Long idUserClient = 3L;
        List<EmployeeEntity> employeeEntity = getListEmployees();
        List<OrderEntity> orderEntityStatus = orderEntityEmpty();
        OrderEntity orderEntity = orderEntity();
        List<OrderPlateEntity> orderPlateList = orderPlateEntity();
        Authentication authentication = mock(Authentication.class);

        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user = mock(UserDetails.class);
        String expectedUsername = "client@gmail.com";

        //WHEN
        //Le envio los datos correctamente
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserClient, HttpStatus.OK));
        when(restaurantRepository.existsById(order.getIdRestaurant())).thenReturn(true);
        when(employeeRepository.findByIdRestaurant(order.getIdRestaurant())).thenReturn(employeeEntity);
        when(orderRepository.findAllByIdClient(idUserClient)).thenReturn(orderEntityStatus);
        when(orderEntityMapper.toOderEntity(order)).thenReturn(orderEntity);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(orderPlateEntityMapper.toEntity(order.getOrderPlates().get(0))).thenReturn(orderPlateList.get(0));
        when(orderPlateRepository.saveAll(orderPlateList)).thenReturn(orderPlateList);
        when(orderEntityMapper.toOrderModel(orderEntity)).thenReturn(new OrderModel());

        //Then
        //El sistema me guarda la nueva orden en la base de datos
        assertNotNull(orderJpaAdapter.saveOrder(order));
    }

    @Test
    void throwNoDataFoundExceptionWhenAttemptSaveOrderAndRestaurantNotExist() {
        //Given
        //Yo como cliente de la plazoleta quiero guardar un plato asociandole un restaurante que no exista
        OrderModel order = orderModel();
        Long idUserClient = 3L;
        Authentication authentication = mock(Authentication.class);

        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user = mock(UserDetails.class);
        String expectedUsername = "client@gmail.com";

        //When
        //Le envio un id de restaurante que no existe
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserClient, HttpStatus.OK));
        when(restaurantRepository.existsById(order.getIdRestaurant())).thenReturn(false);
        when(employeeRepository.findByIdRestaurant(order.getIdRestaurant())).thenReturn(new ArrayList<>());
        //Then
        //El sistema me devuelve una excepcion NoDataFoundException
        assertThrows(
                NoDataFoundException.class, () -> orderJpaAdapter.saveOrder(order));

    }

    @Test
    void throwNoDataFoundExceptionWhenAttemptSaveOrderAndRestaurantNotEmployeeHaveExist() {
        //Given
        //Yo como cliente de la plazoleta quiero guardar un plato asociandole un restaurante que no exista
        OrderModel order = orderModel();
        Long idUserClient = 3L;
        Authentication authentication = mock(Authentication.class);

        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user = mock(UserDetails.class);
        String expectedUsername = "client@gmail.com";

        //When
        //Le envio un id de un restaurante que no tiene empleados asociados
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserClient, HttpStatus.OK));
        when(restaurantRepository.existsById(order.getIdRestaurant())).thenReturn(true);
        when(employeeRepository.findByIdRestaurant(order.getIdRestaurant())).thenReturn(new ArrayList<>());
        //Then
        //El sistema me devuelve una excepcion NoDataFoundException
        assertThrows(
                NoDataFoundException.class, () -> orderJpaAdapter.saveOrder(order));

    }

    @Test
    void throwAlreadyStatusOrderIsInProcessExceptionWhenAttemptSaveOrderAndHaveOrdersInProcess() {
        //Given
        //Yo como cliente de la plazoleta quiero guardar un plato asociandole un restaurante que no exista
        OrderModel order = orderModel();
        Long idUserClient = 3L;
        List<EmployeeEntity> employeeEntity = getListEmployees();
        List<OrderEntity> orderEntityStatus = orderEntityList();
        Authentication authentication = mock(Authentication.class);
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user = mock(UserDetails.class);
        String expectedUsername = "client@gmail.com";

        //When
        //Le envio una order que tiene una orden en proceso
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserClient, HttpStatus.OK));
        when(restaurantRepository.existsById(order.getIdRestaurant())).thenReturn(true);
        when(employeeRepository.findByIdRestaurant(order.getIdRestaurant())).thenReturn(employeeEntity);
        when(orderRepository.findAllByIdClient(idUserClient)).thenReturn(orderEntityStatus);
        //Then
        //El sistema me devuelve una excepcion NoDataFoundException
        assertThrows(
                AlreadyStatusOrderIsInProcessException.class, () -> orderJpaAdapter.saveOrder(order)
        );

    }

    @Test
    void mustListPaginatedOrdersByStatus() {
        //Given
        //Yo como empleado de la plazoleta quiero listar las ordenes por estado
        String status = "PENDIENTE";
        Integer numberElementsPerPage = 0;
        Integer size = 2;
        Long idUserEmployee = 5L;
        Optional<EmployeeEntity> employeeEntity = Optional.of(getEmployeeEntity());
        List<OrderEntity> orderEntity = orderEntityList();
        Page<OrderEntity> pageOrder = new PageImpl<>(orderEntity);
        Authentication authentication = mock(Authentication.class);
        List<OrderModel> orderModel = orderModelList();
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user = mock(UserDetails.class);
        String expectedUsername = "employee@gmail.com";

        //When
        //Le envio un los datos correctamente
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserEmployee, HttpStatus.OK));
        when(employeeRepository.findByIdPeople(idUserEmployee)).thenReturn(employeeEntity);
        when(orderRepository.findAllByIdRestaurantAndStatus(
                employeeEntity.get().getRestaurantEmployeePK().getIdRestaurant(),
                status,
                PageRequest.of(numberElementsPerPage, size)
        )).thenReturn(pageOrder);
        when(orderEntityMapper.toOrderList(orderEntity)).thenReturn(orderModel);
        //Then
        //El sistema me obtiene los pedidos filtrados por estado y paginado
        assertEquals(orderModel, orderJpaAdapter.listPaginatedOrders(status, numberElementsPerPage, size));
    }

    @Test
    void throwNoDataFoundExceptionWhenAttemptListPaginatedOrdersByStatusAndRestaurantNotEmployeeHaveExist() {
        //Given
        //Yo como empleado de la plazoleta quiero paginar la orden pero no estoy asociado a un restaurante
        String status = "PENDIENTE";
        Integer numberElementsPerPage = 0;
        Integer size = 2;
        Long idUserEmployee = 5L;
        Optional<EmployeeEntity> employeeEntity = Optional.empty();

        Authentication authentication = mock(Authentication.class);
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user = mock(UserDetails.class);
        String expectedUsername = "employee@gmail.com";

        //When
        //Le envio un id de un empleado que no tiene un restaurante asociado
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserEmployee, HttpStatus.OK));
        when(employeeRepository.findByIdPeople(idUserEmployee)).thenReturn(employeeEntity);
        //Then
        //El sistema me devuelve una excepcion NoDataFoundException
        assertThrows(
                NoDataFoundException.class, () -> orderJpaAdapter.listPaginatedOrders(status, numberElementsPerPage, size));

    }

    @Test
    void mustUpdateStatusPendingOrder() {
        //Given
        //Yo como empleado quiero actualizar el estado de la orden que se encuentra  PENDIENTE a EN_PREPARACION
        Long idOrder = 1L;
        String status = "EN_PREPARACION";
        Long idUserEmployee = 3L;
        Optional<OrderEntity> orderEntity = Optional.of(orderEntity());
        Optional<EmployeeEntity> employeeEntity = Optional.of(getEmployeeEntity());
        OrderEntity orderUpdated = orderEntityUpdateStatusPending(idUserEmployee);
        OrderModel orderModel = orderModelUpdateStatusPending(idUserEmployee);

        Authentication authentication = mock(Authentication.class);
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user = mock(UserDetails.class);
        String expectedUsername = "employee@gmail.com";

        //When
        //Le envio un los datos correctos

        when(orderRepository.findById(idOrder)).thenReturn(orderEntity);
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserEmployee, HttpStatus.OK));
        when(employeeRepository.findByIdPeople(idUserEmployee)).thenReturn(employeeEntity);
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderUpdated);
        when(orderEntityMapper.toOrderModel(orderUpdated)).thenReturn(orderModel);
        //Then
        //El sistema me actualiza el estado de la orden
        assertEquals("EN_PREPARACION", orderJpaAdapter.updateStatusPendingOrder(idOrder, status).getStatus());

    }

    @Test
    void throwNoDataFoundExceptionWhenAttemptUpdateStatusPendingOrderAndOrderIsNotFound() {
        //GIVEN
        //Yo como empleado de la plazoleta quiero actualizar el estado de una order que se encuentra
        // PENDIENTE a EN_PREPARACION pero la orden no exite
        Long idOrder = 1L;
        String status = "EN_PREPARACION";

        //When
        //Le envio el id de una ordern inexistente
        when(orderRepository.findById(idOrder)).thenReturn(Optional.empty());

        //Then
        //El sistema me devuelve una excepcion NoDataFoundException
        assertThrows(
                NoDataFoundException.class, () -> orderJpaAdapter.updateStatusPendingOrder(idOrder, status));

    }

    @Test
    void throwStatusOrderIsInProcessExceptionWhenAttemptUpdateStatusPendingOrderAndTheOrderIsReadyInProcess() {
        //GIVEN
        //Yo como empleado de la plazoleta quiero actualizar el estado de una order que se encuentra
        // PENDIENTE a EN_PREPARACION pero la order ya tiene un estado diferente a pendiente
        Long idOrder = 1L;
        String status = "EN_PREPARACION";
        Long idUserEmployee = 3L;
        Optional<OrderEntity> orderEntity = Optional.of(orderEntityUpdateStatusPending(idUserEmployee));

        //When
        //Le envio el id de una ordern que tiene un etado diferente a pendiente
        when(orderRepository.findById(idOrder)).thenReturn(orderEntity);

        //Then
        //El sistema me devuelve una excepcion del tipo StatusOrderIsInProcessException
        assertThrows(
                StatusOrderIsInProcessException.class, () -> orderJpaAdapter.updateStatusPendingOrder(idOrder, status));

    }

    @Test
    void throwNoDataFoundExceptionWhenAttemptUpdateStatusPendingOrderAndTheOrderIsTheEmployeeIsNotAssociateToRestaurant() {
        //GIVEN
        //Yo como empleado de la plazoleta quiero actualizar el estado de una order que se encuentra
        // PENDIENTE a EN_PREPARACION pero no estoy asociado a ningun restaurante
        Long idOrder = 1L;
        String status = "EN_PREPARACION";
        Long idUserEmployee = 5L;
        Optional<OrderEntity> orderEntity = Optional.of(orderEntity());

        Authentication authentication = mock(Authentication.class);
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user = mock(UserDetails.class);
        String expectedUsername = "employee@gmail.com";


        //When
        //El usuario autenticado no esta asociado a un restaurante
        when(orderRepository.findById(idOrder)).thenReturn(orderEntity);
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserEmployee, HttpStatus.OK));
        when(employeeRepository.findByIdPeople(idUserEmployee)).thenReturn(Optional.empty());
        //Then
        //El sistema me devuelve una excepcion del tipo NoDataFoundException
        assertThrows(
                NoDataFoundException.class, () -> orderJpaAdapter.updateStatusPendingOrder(idOrder, status));

    }

    @Test
    void throwRestaurantNotIsAssociatedWithTheEmployeeWhenAttemptUpdateStatusPendingOrderAndTheOrderIsTheEmployeeIsNotAssociateToRestaurant() {
        //GIVEN
        //Yo como empleado de la plazoleta quiero actualizar el estado de una order que se encuentra
        // PENDIENTE a EN_PREPARACION pero estoy asociado a un restaurante diferente al de la orden
        Long idOrder = 1L;
        String status = "EN_PREPARACION";
        Long idUserEmployee = 5L;
        Optional<OrderEntity> orderEntity = Optional.of(orderEntity());
        Optional<EmployeeEntity> employeeEntity = Optional.of(getEmployeeEntityRestaurantDifferent());

        Authentication authentication = mock(Authentication.class);
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user = mock(UserDetails.class);
        String expectedUsername = "employee@gmail.com";


        //When
        //El usuario autenticado no esta asociado a un restaurante
        when(orderRepository.findById(idOrder)).thenReturn(orderEntity);
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserEmployee, HttpStatus.OK));
        when(employeeRepository.findByIdPeople(idUserEmployee)).thenReturn(employeeEntity);
        //Then
        //El sistema me devuelve una excepcion del tipo NoDataFoundException
        assertThrows(
                RestaurantNotIsAssociatedWithTheEmployee.class, () -> orderJpaAdapter.updateStatusPendingOrder(idOrder, status));

    }

    @Test
    void mustSendMessageClientOrderReady() {
        //GIVEN
        //Yo como usuario de la plazoleta quiero notificarle al cliente que el pedido ya se
        //encuentra listo
        String status = "LISTO";
        MessageModel messageModel = messageModel();
        Messaging messaging = messaging();
        Long idUserEmployee = 2L;
        Optional<OrderEntity> orderEntityOpt = Optional.of(orderEntityUpdateStatusPending(idUserEmployee));
        User user = getUser();
        Authentication authentication = mock(Authentication.class);
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = mock(UserDetails.class);
        String expectedUsername = "employee@gmail.com";



        //When
        //Le envio los datos correctamente
        when(messageMapper.toMessage(messageModel)).thenReturn(messaging);
        when(orderRepository.findById(messageModel.getId())).thenReturn(orderEntityOpt);
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserEmployee, HttpStatus.OK));
        when(userClient.getUser(token, orderEntityOpt.get().getIdClient())).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));
        when(messagingClient.sendMessaging(messaging)).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(new OrderEntity());

        //Then
        //El sistema me envia el mesaje y actualiza el estado
        orderJpaAdapter.sendMessageClientOrderReady(status, messageModel);
        verify(userClient).getUser(eq(token), any());
        verify(messagingClient).sendMessaging(eq(messaging));
    }

    @Test
    void throwNoDataFoundExceptionWhenAttemptSendMessageClientOrderReadyAndOrderIsNotFound() {
        //GIVEN
        //Yo como empleado de la plazoleta quiero notificar al cliente que la orden
        //ya esta lista pero la orden no existe
        String status = "LISTO";
        MessageModel messageModel = messageModel();
        Messaging messaging = messaging();

        //When
        //le envio el id de una orden que no existe
        when(messageMapper.toMessage(messageModel)).thenReturn(messaging);
        when(orderRepository.findById(messageModel.getId())).thenReturn(Optional.empty());

        //Then
        //El sistema me devuelve una excepcion NoDataFoundException
        assertThrows(
                NoDataFoundException.class, () -> orderJpaAdapter.sendMessageClientOrderReady(status, messageModel));

    }

    @Test
    void throwStatusOrderNotIsReadyExceptionWhenAttemptSendMessageClientOrderReadyAndTheOrderIsNotPreparation() {
        //GIVEN
        //Yo como empleado de la plazoleta quiero notificar al cliente que la orden
        //ya esta lista pero la orden en realidad no tiene ese estado
        String status = "LISTO";
        MessageModel messageModel = messageModel();
        Messaging messaging = messaging();
        Optional<OrderEntity> orderEntityOpt = Optional.of(orderEntity());
        Long idUserEmployee = 18L;

        Authentication authentication = mock(Authentication.class);
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = mock(UserDetails.class);
        String expectedUsername = "employee@gmail.com";
        //When
        //le envio el id de una orden que no esta lista
        when(messageMapper.toMessage(messageModel)).thenReturn(messaging);
        when(orderRepository.findById(messageModel.getId())).thenReturn(orderEntityOpt);
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserEmployee, HttpStatus.OK));
        //Then
        //El sistema me devuelve una excepcion StatusOrderNotIsReadyException
        assertThrows(
                StatusOrderNotIsReadyException.class, () -> orderJpaAdapter.sendMessageClientOrderReady(status, messageModel));


    }

    @Test
    void mustDeliverOrderClient() {
        //GIVEN
        //yo como empleado de la plazoleta quiero entragar una orden
        Integer code = 55555;
        Long id = 6L;
        String status = "ENTREGADO";
        Optional<OrderEntity> orderEntityOpt = Optional.of(orderEntityIsReady());
        Long idUserEmployee = 18L;

        Authentication authentication = mock(Authentication.class);
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = mock(UserDetails.class);
        String expectedUsername = "employee@gmail.com";
        //When
        //Envio los datos correctos
        when(orderRepository.findById(id)).thenReturn(orderEntityOpt);
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserEmployee, HttpStatus.OK));
        when(messagingClient.validateCode(code, id)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(new OrderEntity());

        //Then
        //El sistema actualiza el estado a ENTREGADO
        orderJpaAdapter.deliverOrderClient(code, id, status);
        verify(messagingClient).validateCode(eq(code), any());

    }

    @Test
    void throwNoDataFoundExceptionWhenAttemptDeliverOrderClientAndOrderIsNotFound() {
        //GIVEN
        //Yo como empleado de la plazoleta quiero notificar al cliente que la orden
        //ya esta lista pero la orden no existe
        Integer code = 55555;
        Long id = 6L;
        String status = "ENTREGADO";

        //When
        //le envio el id de una orden que no existe
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        //Then
        //El sistema me devuelve una excepcion NoDataFoundException
        assertThrows(
                NoDataFoundException.class, () -> orderJpaAdapter.deliverOrderClient(code, id, status));

    }

    @Test
    void throwStatusOrderNotIsReadyExceptionWhenAttemptDeliverOrderClientAndTheOrderIsNotPreparation() {
        //GIVEN
        //Yo como empleado de la plazoleta quiero notificar al cliente que la orden
        //ya esta lista pero la orden en realidad no tiene ese estado
        Integer code = 55555;
        Long id = 6L;
        String status = "ENTREGADO";
        Optional<OrderEntity> orderEntityOpt = Optional.of(orderEntity());

        Long idUserEmployee = 18L;

        Authentication authentication = mock(Authentication.class);
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = mock(UserDetails.class);
        String expectedUsername = "employee@gmail.com";
        //When
        //le envio el id de una orden que no esta lista
        when(orderRepository.findById(id)).thenReturn(orderEntityOpt);
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserEmployee, HttpStatus.OK));
        //Then
        //El sistema me devuelve una excepcion StatusOrderNotIsReadyException
        assertThrows(
                StatusOrderNotIsReadyException.class, () -> orderJpaAdapter.deliverOrderClient(code, id, status));

    }
    @Test
    void mustCancelOrder(){
        //GIVEN
        //yo como CLIENTE de la plazoleta quiero cancelar una orden
        Long id = 6L;
        String status = "CANCELADO";
        Optional<OrderEntity> orderEntityOpt = Optional.of(orderEntity());

        Long idUserClient = 17L;

        Authentication authentication = mock(Authentication.class);
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = mock(UserDetails.class);
        String expectedUsername = "employee@gmail.com";

        //When
        //Envio los datos correctos
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserClient, HttpStatus.OK));
        when(orderRepository.findById(id)).thenReturn(orderEntityOpt);
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(new OrderEntity());

        //Then
        //El sistema actualiza el estado a CANCELADO
        orderJpaAdapter.cancelOrder(id, status);
        verify(orderRepository).save(any(OrderEntity.class));

    }

    @Test
    void throwNoDataFoundExceptionWhenAttemptCancelOrderAndOrderIsNotFound() {
        //GIVEN
        //Yo como cliente de la plazoleta quiero cancelar una orden pero la orden no existe
        Long id = 6L;
        String status = "CANCELADO";

        Long idUserClient = 17L;

        Authentication authentication = mock(Authentication.class);
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = mock(UserDetails.class);
        String expectedUsername = "employee@gmail.com";
        //When
        //le envio el id de una orden que no existe
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserClient, HttpStatus.OK));
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        //Then
        //El sistema me devuelve una excepcion NoDataFoundException
        assertThrows(
                NoDataFoundException.class, () -> orderJpaAdapter.cancelOrder(id, status));


    }
    @Test
    void throwStatusOrderInProcessNotCancelExceptionWhenAttemptCancelOrderAndTheOrderIsInProcess(){
        //GIVEN
        //Yo como cliente de la plazoleta quiero cancelar una orden pero la orden ya esta en proceso
        Long id = 6L;
        String status = "CANCELADO";
        Optional<OrderEntity> orderEntityOpt = Optional.of(orderEntityIsReady());

        Long idUserClient = 17L;

        Authentication authentication = mock(Authentication.class);
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9DbGllbnRlIiwic3ViIjoidGljYUBnbWFpbC5jb20iLCJpYXQiOjE2NzcyODk3OTIsImV4cCI6MTY3NzI5MTIzMn0.ZL7F9K0G0cpsZruLoXkmBEFJJqtwcaSAFWrsXcOxblA";
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = mock(UserDetails.class);
        String expectedUsername = "employee@gmail.com";

        //When
        //le envio el id de una orden que no existe
        when(request.getHeader("Authorization")).thenReturn(token);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(expectedUsername);
        when(userClient.getIdUser(token, userLoginApplication())).thenReturn(new ResponseEntity<>(idUserClient, HttpStatus.OK));
        when(orderRepository.findById(id)).thenReturn(orderEntityOpt);

        //Then
        //El sistema me devuelve una excepcion StatusOrderInProcessNotCancelException
        assertThrows(
                StatusOrderInProcessNotCancelException.class, () -> orderJpaAdapter.cancelOrder(id, status));
    }


}