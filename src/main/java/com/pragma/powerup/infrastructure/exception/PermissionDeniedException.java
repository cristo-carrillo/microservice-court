package com.pragma.powerup.infrastructure.exception;

public class PermissionDeniedException extends RuntimeException{

    public PermissionDeniedException(String msg){
        super(msg);
    }
}
