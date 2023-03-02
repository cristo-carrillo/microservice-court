package com.pragma.powerup.infrastructure.order.out.jpa.adapter;

import com.pragma.powerup.domain.order.model.MessageModel;
import com.pragma.powerup.domain.order.model.OrderModel;
import com.pragma.powerup.domain.order.spi.IOrderPersistencePort;
import com.pragma.powerup.infrastructure.client.IMessagingClient;
import com.pragma.powerup.infrastructure.client.IUserClient;
import com.pragma.powerup.infrastructure.client.model.User;
import com.pragma.powerup.infrastructure.client.model.messaging.IMessageMapper;
import com.pragma.powerup.infrastructure.client.model.messaging.Messaging;
import com.pragma.powerup.infrastructure.employee.out.jpa.entity.EmployeeEntity;
import com.pragma.powerup.infrastructure.employee.out.jpa.repository.IEmployeeRepository;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.exception.StatusOrderInProcessNotCancelException;
import com.pragma.powerup.infrastructure.exception.StatusOrderIsInProcessException;
import com.pragma.powerup.infrastructure.exception.StatusOrderNotIsReadyException;
import com.pragma.powerup.infrastructure.order.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.order.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup.infrastructure.order.out.jpa.repository.IOrderRepository;
import com.pragma.powerup.infrastructure.orderplate.out.jpa.entity.OrderPlateEntity;
import com.pragma.powerup.infrastructure.orderplate.out.jpa.mapper.IOrderPlateEntityMapper;
import com.pragma.powerup.infrastructure.orderplate.out.jpa.repository.IOrderPlateRepository;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pragma.powerup.domain.order.status.Status.*;
import static com.pragma.powerup.infrastructure.order.utils.ValidateStatusClient.validateStatusClient;
import static com.pragma.powerup.infrastructure.utils.UserLogin.userLoginApplication;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {
    private final IMessageMapper messageMapper;
    private final IMessagingClient messagingClient;
    private final HttpServletRequest request;
    private final IUserClient userClient;
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IRestaurantRepository restaurantRepository;
    private final IOrderPlateRepository orderPlateRepository;
    private final IEmployeeRepository employeeRepository;
    private final IOrderPlateEntityMapper orderPlateEntityMapper;

    @Override
    public OrderModel saveOrder(OrderModel order) {
        Long idUserClient = userClient.getIdUser(request.getHeader("Authorization"), userLoginApplication()).getBody();
        if(!restaurantRepository.existsById(order.getIdRestaurant())){
            throw new NoDataFoundException();
        }
        List<EmployeeEntity> employeeEntity = employeeRepository.findByIdRestaurant(order.getIdRestaurant());
        if(employeeEntity.isEmpty()){
            throw new NoDataFoundException();
        }
        List<OrderEntity> orderEntityStatus = orderRepository.findAllByIdClient(idUserClient);
        validateStatusClient(orderEntityStatus.stream().map(o -> o.getStatus()).collect(Collectors.toList()));
        order.setIdChef(employeeEntity.get(0).getRestaurantEmployeePK().getIdPeople());
        order.setIdClient(idUserClient);
        OrderEntity orderEntity =  orderRepository.save(orderEntityMapper.toOderEntity(order));
        List<OrderPlateEntity> orderPlateList = order.getOrderPlates().stream()
                .peek(orders -> orders.setIdOrder(orderEntity.getId()))
                .map(orderPlateEntityMapper::toEntity)
                .collect(Collectors.toList());
        orderPlateRepository.saveAll(orderPlateList);
        return orderEntityMapper.toOrderModel(orderEntity);
    }

    @Override
    public List<OrderModel> listPaginatedOrders(String status, Integer numberElementsPerPage, Integer size) {
        Long idUserEmployee = userClient.getIdUser(request.getHeader("Authorization"), userLoginApplication()).getBody();
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findByIdPeople(idUserEmployee);
        if(employeeEntity.isEmpty()){
            throw new NoDataFoundException();
        }
        List<OrderEntity> listOrders = orderRepository.findAllByIdRestaurantAndStatus(
                employeeEntity.get().getRestaurantEmployeePK().getIdRestaurant(),
                status,
                PageRequest.of(numberElementsPerPage, size)
                ).toList();

        return orderEntityMapper.toOrderList(listOrders);
    }

    @Override
    public OrderModel updateStatusPendingOrder(Long idOrder, String status) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(idOrder);
        if(orderEntity.isEmpty()) {
            throw new NoDataFoundException();
        }
        OrderEntity orderEntityUpdate = orderEntity.get();
        if(!orderEntityUpdate.getStatus().equals(PENDING.getStatus())){
            throw new StatusOrderIsInProcessException("status " + orderEntityUpdate.getStatus());
        }
        Long idUserEmployee = userClient.getIdUser(request.getHeader("Authorization"), userLoginApplication()).getBody();
        orderEntityUpdate.setStatus(status);
        orderEntityUpdate.setIdChef(idUserEmployee);
        OrderEntity orderUpdated = orderRepository.save(orderEntityUpdate);
        return orderEntityMapper.toOrderModel(orderUpdated);
    }

    @Override
    public void sendMessageClientOrderReady(String status, MessageModel message) {
        Messaging messaging = messageMapper.toMessage(message);
        Optional<OrderEntity> orderEntityOpt = orderRepository.findById(messaging.getId());
        if(orderEntityOpt.isEmpty()){
            throw new NoDataFoundException();
        }
        OrderEntity orderEntity = orderEntityOpt.get();
        if(!orderEntity.getStatus().equals(IN_PREPARATION.getStatus())){
            throw new StatusOrderNotIsReadyException("status "+orderEntity.getStatus());
        }
        User user = userClient.getUser(request.getHeader("Authorization"), orderEntity.getIdClient()).getBody();
        messaging.setPhoneNumber(user.getPhone());
        messagingClient.sendMessaging(messaging);
        orderEntity.setStatus(status);
        orderRepository.save(orderEntity);
    }

    @Override
    public void deliverOrderClient(Integer code, Long id, String status) {
        Optional<OrderEntity> orderEntityOpt = orderRepository.findById(id);
        if(orderEntityOpt.isEmpty()){
            throw new NoDataFoundException();
        }
        OrderEntity orderEntity = orderEntityOpt.get();
        if(!orderEntity.getStatus().equals(READY.getStatus())){
            throw new StatusOrderNotIsReadyException("status "+orderEntity.getStatus());
        }
        messagingClient.validateCode(code, id);
        orderEntity.setStatus(status);
        orderRepository.save(orderEntity);
    }

    @Override
    public void cancelOrder(Long id, String status) {
        Optional<OrderEntity> orderEntityOpt = orderRepository.findById(id);
        if (orderEntityOpt.isEmpty()) {
            throw new NoDataFoundException();
        }
        OrderEntity orderEntity = orderEntityOpt.get();
        if(!orderEntity.getStatus().equals(PENDING.getStatus())){
            throw new StatusOrderInProcessNotCancelException("status "+orderEntity.getStatus());
        }
        orderEntity.setStatus(status);
        orderRepository.save(orderEntity);
    }

}
