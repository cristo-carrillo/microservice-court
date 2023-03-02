package com.pragma.powerup.domain.orderplates.spi;

import com.pragma.powerup.domain.orderplates.model.OrderPlatesModel;

import java.util.List;

public interface IOrderPlatesPersistencePort {

    List<OrderPlatesModel> saveOrderPlates(List<OrderPlatesModel> orderPlates);
}
