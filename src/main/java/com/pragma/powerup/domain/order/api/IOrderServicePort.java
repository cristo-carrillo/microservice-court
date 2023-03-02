package com.pragma.powerup.domain.order.api;

import com.pragma.powerup.domain.order.model.OrderModel;

import java.util.List;

public interface IOrderServicePort {

    void saveOrder(OrderModel order);

    List<OrderModel> listPaginatedOrders(String status, Integer numberElementsPerPage, Integer size);

    OrderModel updateStatusPendingOrder(Long idOrder);

    void sendMessageClientOrderReady(Long id, String message);

    void deliverOrderClient(Integer code, Long id);
    void cancelOrder(Long id);
}
