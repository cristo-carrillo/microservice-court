package com.pragma.powerup.infrastructure.exception;

public class AlreadyStatusOrderIsInProcessException extends RuntimeException{

    public AlreadyStatusOrderIsInProcessException(String msg){
        super(msg);
    }
}
