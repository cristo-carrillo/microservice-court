package com.pragma.powerup.factory;

import com.pragma.powerup.infrastructure.client.model.RolDto;
import com.pragma.powerup.infrastructure.client.model.User;

public class FactoryUserDataTest {

    public static User getUser(){
        return new User(
                1L,
                "name",
                "lastName",
                12345L,
                "+573214366409",
                "name@gmail.com",
                "password",
                new RolDto(
                        1L,
                        "ROLE_Cliente"
                )
        );
    }
}
