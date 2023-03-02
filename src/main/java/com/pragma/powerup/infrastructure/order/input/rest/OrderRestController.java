package com.pragma.powerup.infrastructure.order.input.rest;

import com.pragma.powerup.application.order.dto.request.OrderRequestDto;
import com.pragma.powerup.application.order.dto.response.OrderResponseDto;
import com.pragma.powerup.application.order.handler.IOrderHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final IOrderHandler orderHandler;

    @RolesAllowed("ROLE_Cliente")
    @Operation(summary = "Add a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "order created", content = @Content),
            @ApiResponse(responseCode = "401", description = "Permission denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bad Request (e.g., The value is empty," +
                    " The length value is invalid, The number format is invalid)"
                    , content = @Content),
            @ApiResponse(responseCode = "409", description = "Is already exists", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> saveEmployee(@RequestBody OrderRequestDto orderRequestDto) {
        orderHandler.saveOrder(orderRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "list paginated orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "orders returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OrderResponseDto.class)))),
            @ApiResponse(responseCode = "401", description = "Permission denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bad Request (e.g., The value is empty," +
                    " The length value is invalid, The number format is invalid)"
                    , content = @Content),
            @ApiResponse(responseCode = "409", description = "Is already exists", content = @Content)
    })
    @RolesAllowed("ROLE_Empleado")
    @GetMapping("")
    public ResponseEntity<List<OrderResponseDto>> listPaginatedOrders(@RequestParam(name = "status") String status,
                                                                      @RequestParam(name = "page", defaultValue = "1") Integer numberElementsPerPage,
                                                                      @RequestParam(name = "size", defaultValue = "1") Integer size) {
        return ResponseEntity.ok(orderHandler.listPaginatedOrders(status, numberElementsPerPage, size));
    }

    @RolesAllowed("ROLE_Empleado")
    @Operation(summary = "update a Plate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "order returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "order not exist", content = @Content)
    })
    @PutMapping("/status/preparation/{id}")
    public ResponseEntity<OrderResponseDto> updateStatusPendingOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderHandler.updateStatusPendingOrder(id));
    }

    @RolesAllowed("ROLE_Empleado")
    @Operation(summary = "notify client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "notify send",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "order not exist", content = @Content)
    })
    @PostMapping("/message/{id}")
    public ResponseEntity<Void> sendMessageClientOrderReady(@PathVariable Long id,
                                                            @RequestParam(name = "message", defaultValue = "The order is ready") String message) {
        orderHandler.sendMessageClientOrderReady(id, message);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RolesAllowed("ROLE_Empleado")
    @Operation(summary = "deliver order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deliver order",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "order not exist", content = @Content)
    })
    @PostMapping("/deliver/{code}/{id}")
    public ResponseEntity<Void> deliverOrderClient(@PathVariable Integer code,
                                                            @PathVariable Long id) {
        orderHandler.deliverOrderClient(code, id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RolesAllowed("ROLE_Cliente")
    @Operation(summary = "cancel order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "cancel order",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "order not exist", content = @Content)
    })
    @PutMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelOrderClient(@PathVariable Long id) {
        orderHandler.cancelOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
