package com.pragma.powerup.domain.employee.model;

public class EmployeeModel {

    private Long idRestaurant;
    private Long idPeople;

    public EmployeeModel() {
    }

    public EmployeeModel(Long idRestaurant, Long idPeople) {
        this.idRestaurant = idRestaurant;
        this.idPeople = idPeople;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public Long getIdPeople() {
        return idPeople;
    }

    public void setIdPeople(Long idPeople) {
        this.idPeople = idPeople;
    }
}
