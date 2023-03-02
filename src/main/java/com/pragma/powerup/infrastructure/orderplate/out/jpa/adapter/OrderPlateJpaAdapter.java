package com.pragma.powerup.infrastructure.orderplate.out.jpa.adapter;

import com.pragma.powerup.domain.orderplates.model.OrderPlatesModel;
import com.pragma.powerup.domain.orderplates.spi.IOrderPlatesPersistencePort;
import com.pragma.powerup.infrastructure.orderplate.out.jpa.entity.OrderPlateEntity;
import com.pragma.powerup.infrastructure.orderplate.out.jpa.mapper.IOrderPlateEntityMapper;
import com.pragma.powerup.infrastructure.orderplate.out.jpa.repository.IOrderPlateRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class OrderPlateJpaAdapter implements IOrderPlatesPersistencePort {
    private final IOrderPlateRepository orderPlateRepository;
    private final IOrderPlateEntityMapper orderPlateEntityMapper;
    @Override
    public List<OrderPlatesModel> saveOrderPlates(List<OrderPlatesModel> orderPlates) {
        List<OrderPlateEntity> listPlates = orderPlateEntityMapper.orderPlates(orderPlates);
        return orderPlateEntityMapper.orderPlatesModel(orderPlateRepository.saveAll(listPlates));
    }
}
