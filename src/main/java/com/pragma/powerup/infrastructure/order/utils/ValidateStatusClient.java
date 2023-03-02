package com.pragma.powerup.infrastructure.order.utils;

import com.pragma.powerup.infrastructure.exception.AlreadyStatusOrderIsInProcessException;

import java.util.List;

public class ValidateStatusClient {
    private static final String PENDING = "PENDIENTE";
    private static final String IN_PREPARATION = "EN_PREPARACION";
    private static final String READY = "LISTO";

    public static void validateStatusClient(List<String> status) {
        for (String state : status) {
            switch (state) {
                case PENDING:
                case IN_PREPARATION:
                case READY:
                    throw new AlreadyStatusOrderIsInProcessException("Status "+state);
            }
        }
    }
}
