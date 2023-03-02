package com.pragma.powerup.infrastructure.employee.out.jpa.repository;

import com.pragma.powerup.infrastructure.employee.out.jpa.entity.EmployeeEntity;
import com.pragma.powerup.infrastructure.employee.out.jpa.entity.RestaurantEmployeePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IEmployeeRepository extends JpaRepository<EmployeeEntity, RestaurantEmployeePK> {
    @Query("SELECT en FROM EmployeeEntity en WHERE en.restaurantEmployeePK.idRestaurant = :idRestaurant")
    List<EmployeeEntity> findByIdRestaurant(@Param("idRestaurant") Long idRestaurant);
    @Query("SELECT en FROM EmployeeEntity en WHERE en.restaurantEmployeePK.idPeople = :idPeople")
    Optional<EmployeeEntity> findByIdPeople(@Param("idPeople") Long idPeople);



}
