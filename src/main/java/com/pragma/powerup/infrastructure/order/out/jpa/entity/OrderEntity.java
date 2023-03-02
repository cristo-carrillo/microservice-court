package com.pragma.powerup.infrastructure.order.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pedidos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "id_cliente")
    private Long idClient;
    @Column(name = "fecha")
    private Date date;
    @Column(name = "estado")
    private String status;
    @Column(name = "id_chef")
    private Long idChef;
    @Column(name = "id_restaurante")
    private Long idRestaurant;
}
