package com.pragma.powerup.infrastructure.orderplate.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
@Setter
public class OrderPlatePK implements Serializable {
    @Column(name = "id_pedido")
    private Long idOrder;
    @Column(name = "id_plato")
    private Long idPlate;
}
