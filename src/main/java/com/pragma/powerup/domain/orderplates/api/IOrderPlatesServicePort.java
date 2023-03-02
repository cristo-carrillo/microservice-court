package com.pragma.powerup.domain.orderplates.api;

import com.pragma.powerup.domain.orderplates.model.OrderPlatesModel;

import java.util.List;

public interface IOrderPlatesServicePort {
    void saveOrderPlates(List<OrderPlatesModel> orderPlates);
}
