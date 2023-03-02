package com.pragma.powerup.domain.order.spi;

import com.pragma.powerup.domain.order.model.MessageModel;
import com.pragma.powerup.domain.order.model.OrderModel;

import java.util.List;

public interface IOrderPersistencePort {

    OrderModel saveOrder(OrderModel order);
    List<OrderModel> listPaginatedOrders(String status, Integer numberElementsPerPage, Integer size );
    OrderModel updateStatusPendingOrder(Long idOrder, String status);
    void sendMessageClientOrderReady(String status, MessageModel message);
    void deliverOrderClient(Integer code, Long id, String status);
    void cancelOrder(Long id, String status);
}
