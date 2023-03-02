package com.pragma.powerup.infrastructure.client;

import com.pragma.powerup.infrastructure.client.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microservice-users", path = "/api/v1", url = "http://localhost:8082")
public interface IUserClient {
    @PostMapping("/auth/authenticate")
    ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    );
    @GetMapping("/auth/{email}")
    ResponseEntity<UserDto> getUserAuth(@PathVariable String email);
    @GetMapping("/user/{id}")
    ResponseEntity<User> getUser(@RequestHeader("Authorization") String token,
            @PathVariable Long id);
    @GetMapping("/user/userRol/{id}")
    ResponseEntity<Void> validateRolUser(@RequestHeader("Authorization") String token, @PathVariable Long id);
    @GetMapping("/user/owner/{id}/{username}")
    ResponseEntity<Void> validateOwner(@RequestHeader("Authorization") String token,
                                       @PathVariable Long id, @PathVariable String username );
    @GetMapping("/user/getId/{username}")
    ResponseEntity<Long> getIdUser(@RequestHeader("Authorization") String token,
                                    @PathVariable String username);
    @PostMapping("/user/owner/save_employee")
    public ResponseEntity<Long> saveUserEmployee(@RequestHeader("Authorization") String token,
                                                 @RequestBody UserRequestDto userDto);
}
