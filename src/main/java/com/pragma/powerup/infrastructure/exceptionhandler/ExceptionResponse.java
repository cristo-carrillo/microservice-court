package com.pragma.powerup.infrastructure.exceptionhandler;

public enum ExceptionResponse {
    NO_DATA_FOUND("No data found for the requested petition"),
    PERMISSION_DENIED("Permission denied"),
    ALREADY_EXISTS("Is already exists"),
    FORMAT_VALUE("The format value is not supported"),
    LENGTH_VALUE("The length value is not supported"),
    NUMBER_PHONE_FORMAT("The number format is invalid"),
    NUMBER_RANGE("The number range is invalid"),
    EXPIRED_JWT("The token has expired"),
    ALREADY_STATUS_ORDER_IS_IN_PROCESS("You have an order in process"),
    STATUS_ORDER_NOT_IS_READY("The order is not ready"),
    STATUS_ORDER_IN_PROCESS_NOT_CANCEL("Lo sentimos, tu pedido ya está en preparación y no puede cancelarse");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }


    public String getMessage() {
        return this.message;
    }
}