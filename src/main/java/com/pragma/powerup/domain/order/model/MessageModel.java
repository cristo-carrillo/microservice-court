package com.pragma.powerup.domain.order.model;

public class MessageModel {
    private Long id;
    private String phoneNumber;
    private String message;

    public MessageModel() {
    }

    public MessageModel(Long id, String phoneNumber, String message) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
