package com.pragma.powerup.infrastructure.utils;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserLoginTest {
    @Test
    void mustUserLoginAplication(){
        //Given
        //Yo como usuario quiero obtener el usuario que se encuentra logueado
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user = mock(UserDetails.class);
        String expectedUsername = "USER_OWNER";
        //When
        //Le envio los datos correctamente
        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(expectedUsername);

        //Then
        //El sistema me retorna el usuario que se encuentra autenticado
        assertEquals(expectedUsername, UserLogin.userLoginApplication());
    }

}