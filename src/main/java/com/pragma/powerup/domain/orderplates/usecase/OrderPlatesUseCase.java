package com.pragma.powerup.domain.orderplates.usecase;

import com.pragma.powerup.domain.orderplates.api.IOrderPlatesServicePort;
import com.pragma.powerup.domain.orderplates.model.OrderPlatesModel;
import com.pragma.powerup.domain.orderplates.spi.IOrderPlatesPersistencePort;

import java.util.List;

public class OrderPlatesUseCase implements IOrderPlatesServicePort {
    private final IOrderPlatesPersistencePort orderPlatesPersistencePort;

    public OrderPlatesUseCase(IOrderPlatesPersistencePort orderPlatesPersistencePort) {
        this.orderPlatesPersistencePort = orderPlatesPersistencePort;
    }

    @Override
    public void saveOrderPlates(List<OrderPlatesModel> orderPlates) {
       orderPlatesPersistencePort.saveOrderPlates(orderPlates);
    }
}
