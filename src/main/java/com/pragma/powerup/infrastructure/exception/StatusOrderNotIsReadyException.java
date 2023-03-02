package com.pragma.powerup.infrastructure.exception;

public class StatusOrderNotIsReadyException extends RuntimeException{
    public StatusOrderNotIsReadyException(String msg){
        super(msg);
    }
}
