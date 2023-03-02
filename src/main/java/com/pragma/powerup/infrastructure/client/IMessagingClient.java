package com.pragma.powerup.infrastructure.client;

import com.pragma.powerup.infrastructure.client.model.messaging.Messaging;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "microservice-messaging", path = "/api/v1/messaging", url = "http://localhost:8083")
public interface IMessagingClient {
    @PostMapping("")
    ResponseEntity<Void> sendMessaging(@RequestBody Messaging messaging);

    @PostMapping("/validate-code/{code}/{id}")
    ResponseEntity<Void> validateCode(@PathVariable Integer code, @PathVariable Long id);
}
