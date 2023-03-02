package com.pragma.powerup.infrastructure.employee.out.jpa.entity;

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
public class RestaurantEmployeePK implements Serializable {
    @Column(name = "id_restaurante")
    private Long idRestaurant;
    @Column(name = "id_Persona")
    private Long idPeople;

}
