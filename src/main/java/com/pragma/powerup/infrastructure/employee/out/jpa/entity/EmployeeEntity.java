package com.pragma.powerup.infrastructure.employee.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "retaurante_empleado")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeEntity {
    @EmbeddedId
    private RestaurantEmployeePK restaurantEmployeePK;

}
