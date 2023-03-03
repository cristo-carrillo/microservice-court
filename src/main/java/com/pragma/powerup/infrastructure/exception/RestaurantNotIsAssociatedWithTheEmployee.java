package com.pragma.powerup.infrastructure.exception;

public class RestaurantNotIsAssociatedWithTheEmployee extends RuntimeException{
    public RestaurantNotIsAssociatedWithTheEmployee(String msg){
        super(msg);
    }
}
