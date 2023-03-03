package com.pragma.powerup.infrastructure.exceptionhandler;

import com.pragma.powerup.domain.plate.exception.NumberRangException;
import com.pragma.powerup.domain.restaurant.exception.FormatValueException;
import com.pragma.powerup.domain.restaurant.exception.LengthValueException;
import com.pragma.powerup.domain.restaurant.exception.NumberPhoneFormatException;
import com.pragma.powerup.infrastructure.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.awt.*;
import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            NoDataFoundException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NO_DATA_FOUND.getMessage()));
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<Map<String, String>> handlePermissionDeniedException(
            PermissionDeniedException ignoredPermissionDeniedException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.PERMISSION_DENIED.getMessage()
                        + " : " + ignoredPermissionDeniedException.getMessage()));
    }

    @ExceptionHandler(FormatValueException.class)
    public ResponseEntity<Map<String, String>> handleFontFormatException(
            FormatValueException ignoredFormatValueException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.FORMAT_VALUE.getMessage()
                        + " : " + ignoredFormatValueException.getMessage()));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleAlreadyExistsException(
            AlreadyExistsException ignoredAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.ALREADY_EXISTS.getMessage()
                        + " : " + ignoredAlreadyExistsException.getMessage()));
    }

    @ExceptionHandler(LengthValueException.class)
    public ResponseEntity<Map<String, String>> handleLengthValueException(
            LengthValueException ignoredLengthValueException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.LENGTH_VALUE.getMessage()
                        + " : " + ignoredLengthValueException.getMessage()));
    }

    @ExceptionHandler(NumberPhoneFormatException.class)
    public ResponseEntity<Map<String, String>> handleNumberFormatException(
            NumberPhoneFormatException ignoredNumberPhoneFormatException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NUMBER_PHONE_FORMAT.getMessage()
                        + " : " + ignoredNumberPhoneFormatException.getMessage()));
    }

    @ExceptionHandler(NumberRangException.class)
    public ResponseEntity<Map<String, String>> handleNumberRangException(
            NumberRangException ignoredNumberRangException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NUMBER_RANGE.getMessage()
                        + " : " + ignoredNumberRangException.getMessage()));
    }

    @ExceptionHandler(StatusOrderInProcessNotCancelException.class)
    public ResponseEntity<Map<String, String>> handleStatusOrderInProcessNotCancelException(
            StatusOrderInProcessNotCancelException statusOrderInProcessNotCancelException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.STATUS_ORDER_IN_PROCESS_NOT_CANCEL.getMessage()
                + ": " + statusOrderInProcessNotCancelException.getMessage()));
    }

    @ExceptionHandler(StatusOrderNotIsReadyException.class)
    public ResponseEntity<Map<String, String>> handleStatusOrderNotIsReadyException(
            StatusOrderNotIsReadyException statusOrderNotIsReadyException ){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.STATUS_ORDER_NOT_IS_READY.getMessage()
                + ": " + statusOrderNotIsReadyException.getMessage()));
    }


    @ExceptionHandler(AlreadyStatusOrderIsInProcessException.class)
    public ResponseEntity<Map<String, String>> handleAlreadyStatusOrderIsInProcessException(
            AlreadyStatusOrderIsInProcessException alreadyStatusOrderIsInProcessException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.ALREADY_STATUS_ORDER_IS_IN_PROCESS.getMessage()
                        + " : " + alreadyStatusOrderIsInProcessException.getMessage()));
    }


    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Map<String, String>> handleExpiredJwtException(Throwable expiredJwtException){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
          .body(Collections.singletonMap(MESSAGE, ExceptionResponse.EXPIRED_JWT.getMessage()
           + " : " + expiredJwtException.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ignoredException) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Collections.singletonMap(MESSAGE, ignoredException.getMessage()));
    }

}