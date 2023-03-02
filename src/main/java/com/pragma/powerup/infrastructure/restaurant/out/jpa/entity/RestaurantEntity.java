package com.pragma.powerup.infrastructure.restaurant.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "restaurantes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "nombre", nullable = false, length = 100)
    private String name;
    @Column(name = "direccion", nullable = false, length = 100)
    private String address;
    @Column(name = "id_propietario")
    private Long idOwner;
    @Column(name = "telefono", nullable = false, length = 13)
    private String phone;
    @Column(name = "url_logo", length = 100)
    private String urlLogo;
    private Long nit;
}
