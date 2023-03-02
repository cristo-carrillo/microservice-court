package com.pragma.powerup.application.employee.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEmployeeRequestDto {
    private String name;
    private String lastName;

    private Long identityDocument;
    private String phone;
    private String email;
    private String password;
    private Long idRol;
}
