package com.pragma.powerup.domain.orderplates.model;

public class OrderPlatesModel {
    private Long idOrder;
    private Long idPlate;
    private Integer quantity;

    public OrderPlatesModel(Long idOrder, Long idPlate, Integer quantity) {
        this.idOrder = idOrder;
        this.idPlate = idPlate;
        this.quantity = quantity;
    }

    public OrderPlatesModel() {
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Long getIdPlate() {
        return idPlate;
    }

    public void setIdPlate(Long idPlate) {
        this.idPlate = idPlate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
