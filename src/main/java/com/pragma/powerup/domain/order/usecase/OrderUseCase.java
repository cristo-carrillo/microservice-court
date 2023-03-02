package com.pragma.powerup.domain.order.usecase;

import com.pragma.powerup.domain.order.api.IOrderServicePort;
import com.pragma.powerup.domain.order.model.MessageModel;
import com.pragma.powerup.domain.order.model.OrderModel;
import com.pragma.powerup.domain.order.spi.IOrderPersistencePort;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static com.pragma.powerup.domain.order.status.Status.*;

public class OrderUseCase implements IOrderServicePort {
    private final IOrderPersistencePort orderPersistencePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public void saveOrder(OrderModel order) {
        order.setStatus(PENDING.getStatus());
        order.setDate(Date.from(Instant.now()));
        this.orderPersistencePort.saveOrder(order);
    }

    @Override
    public List<OrderModel> listPaginatedOrders(String status, Integer numberElementsPerPage, Integer size) {
        return orderPersistencePort.listPaginatedOrders(status, numberElementsPerPage, size);
    }

    @Override
    public OrderModel updateStatusPendingOrder(Long idOrder) {
        return orderPersistencePort.updateStatusPendingOrder(idOrder, IN_PREPARATION.getStatus());
    }

    @Override
    public void sendMessageClientOrderReady(Long id, String message) {
        MessageModel messageModel = new MessageModel(id, null, message);
        orderPersistencePort.sendMessageClientOrderReady(READY.getStatus(), messageModel);
    }

    @Override
    public void deliverOrderClient(Integer code, Long id) {
        orderPersistencePort.deliverOrderClient(code, id, DELIVERED.getStatus());
    }

    @Override
    public void cancelOrder(Long id) {
         orderPersistencePort.cancelOrder(id, CANCELLED.getStatus());
    }
}
