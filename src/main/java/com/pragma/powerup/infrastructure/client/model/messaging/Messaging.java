package com.pragma.powerup.infrastructure.client.model.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class Messaging {
    private Long id;
    private String phoneNumber;
    private String message;
}
