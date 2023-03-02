package com.pragma.powerup.infrastructure.client.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private Long id;
    private String name;
    private String lastName;
    private Long identityDocument;
    private String phone;
    private String email;
    private String password;
    private Long idRol;
}
