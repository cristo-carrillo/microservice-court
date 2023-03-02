package com.pragma.powerup.infrastructure.exception;

public class StatusOrderIsInProcessException extends RuntimeException{
    public StatusOrderIsInProcessException(String message){
        super(message);
    }
}
