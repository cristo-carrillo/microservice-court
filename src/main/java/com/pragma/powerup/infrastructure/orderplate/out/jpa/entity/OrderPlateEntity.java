package com.pragma.powerup.infrastructure.orderplate.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos_platos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderPlateEntity {
    @EmbeddedId
    private OrderPlatePK orderPlatePK;
    @Column(name = "cantidad")
    private Integer quantity;
}
