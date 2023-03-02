package com.pragma.powerup.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Security {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers("/api/v1/authentication/**")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/restaurants/**").hasAuthority("ROLE_Administrador")
                .antMatchers(HttpMethod.GET,"/api/v1/restaurants/**").hasAuthority("ROLE_Cliente")
                .antMatchers("/api/v1/plate/**","/api/v1/employee/**").hasAuthority("ROLE_Propietario")
                .antMatchers(HttpMethod.POST,"/api/v1/orders/message/**").hasAuthority("ROLE_Empleado")
                .antMatchers(HttpMethod.POST,"/api/v1/orders/deliver/**").hasAuthority("ROLE_Empleado")
                .antMatchers(HttpMethod.PUT,"/api/v1/orders/cancel/**").hasAuthority("ROLE_Cliente")
                .antMatchers(HttpMethod.POST,"/api/v1/orders/**").hasAuthority("ROLE_Cliente")
                .antMatchers(HttpMethod.GET,"/api/v1/orders/**").hasAuthority("ROLE_Empleado")
                .antMatchers(HttpMethod.PUT,"/api/v1/orders/status/**").hasAuthority("ROLE_Empleado")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

}
