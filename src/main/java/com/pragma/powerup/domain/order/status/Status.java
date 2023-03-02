package com.pragma.powerup.domain.order.status;

public enum Status {
    PENDING("PENDIENTE"),
    IN_PREPARATION("EN_PREPARACION"),
    READY("LISTO"),
    DELIVERED("ENTREGADO"),
    CANCELLED("CANCELADO");

    private String status;

    Status(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
