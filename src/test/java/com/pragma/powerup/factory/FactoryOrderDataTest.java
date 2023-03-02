package com.pragma.powerup.factory;

import com.pragma.powerup.domain.order.model.OrderModel;
import com.pragma.powerup.domain.orderplates.model.OrderPlatesModel;
import com.pragma.powerup.infrastructure.order.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.orderplate.out.jpa.entity.OrderPlateEntity;
import com.pragma.powerup.infrastructure.orderplate.out.jpa.entity.OrderPlatePK;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FactoryOrderDataTest {

    public static List<OrderEntity> orderEntityList(){
        return List.of(
                new OrderEntity(6L, 17L, Date.from(Instant.now()), "EN_PREPARACION", 18L,1L),
                new OrderEntity(7L, 17L, Date.from(Instant.now()), "PENDIENTE", 18L,1L)
        );
    }

    public static List<OrderModel> orderModelList(){
        return List.of(
                new OrderModel(6L, 17L, Date.from(Instant.now()), "EN_PREPARACION", 18L,1L,null),
                new OrderModel(7L, 17L, Date.from(Instant.now()), "PENDIENTE", 18L,1L,null)
        );
    }
    public static List<OrderEntity> orderEntityEmpty(){
        return new ArrayList<>();
    }
    public static OrderEntity orderEntity(){
        return new OrderEntity(6L, 17L, Date.from(Instant.now()), "PENDIENTE", 18L,1L);
    }


    public static OrderEntity orderEntityUpdateStatusPending(Long idUserEmployee){
        return new OrderEntity(6L, 17L, Date.from(Instant.now()), "EN_PREPARACION", idUserEmployee,1L);
    }
    public static OrderModel orderModelUpdateStatusPending(Long idUserEmployee){
        return new OrderModel(6L, 17L, Date.from(Instant.now()), "EN_PREPARACION", idUserEmployee,1L, null);
    }
    public static OrderModel orderModel(){
        return new OrderModel(null, null, Date.from(Instant.now()), "PENDIENTE", 18L,1L,
                List.of(new OrderPlatesModel(null, 2L, 3)));
    }

    public static List<OrderPlateEntity> orderPlateEntity(){
        return List.of(
                new OrderPlateEntity(new OrderPlatePK(6L, 2L),2),
                new OrderPlateEntity(new OrderPlatePK(6L, 3L),3)
        );
    }
}
