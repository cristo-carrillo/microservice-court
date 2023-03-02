package com.pragma.powerup.domain.order.model;

import com.pragma.powerup.domain.orderplates.model.OrderPlatesModel;

import java.util.Date;
import java.util.List;

public class OrderModel {
    private Long id;
    private Long idClient;
    private Date date;
    private String status;
    private Long idChef;
    private Long idRestaurant;
    private List<OrderPlatesModel> orderPlates;

    public OrderModel() {
    }

    public OrderModel(Long id, Long idClient, Date date, String status, Long idChef, Long idRestaurant, List<OrderPlatesModel> orderPlates) {
        this.id = id;
        this.idClient = idClient;
        this.date = date;
        this.status = status;
        this.idChef = idChef;
        this.idRestaurant = idRestaurant;
        this.orderPlates = orderPlates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIdChef() {
        return idChef;
    }

    public void setIdChef(Long idChef) {
        this.idChef = idChef;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public List<OrderPlatesModel> getOrderPlates() {
        return orderPlates;
    }

    public void setOrderPlates(List<OrderPlatesModel> orderPlates) {
        this.orderPlates = orderPlates;
    }
}
