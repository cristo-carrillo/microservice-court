package com.pragma.powerup.infrastructure.client.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long idDto;
    private String nameDto;
    private String lastName;

    private Long identityDocument;
    private String phone;
    private String email;
    private String password;
    private RolDto rolDto;
}
