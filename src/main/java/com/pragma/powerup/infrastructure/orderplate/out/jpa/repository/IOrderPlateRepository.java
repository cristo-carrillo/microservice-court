package com.pragma.powerup.infrastructure.orderplate.out.jpa.repository;

import com.pragma.powerup.infrastructure.orderplate.out.jpa.entity.OrderPlateEntity;
import com.pragma.powerup.infrastructure.orderplate.out.jpa.entity.OrderPlatePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderPlateRepository extends JpaRepository<OrderPlateEntity, OrderPlatePK> {
}
