package com.pragma.powerup.infrastructure.exception;

public class StatusOrderInProcessNotCancelException extends RuntimeException{
    public StatusOrderInProcessNotCancelException(String message){
        super(message);
    }
}
