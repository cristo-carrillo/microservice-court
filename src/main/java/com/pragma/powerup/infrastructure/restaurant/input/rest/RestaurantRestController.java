package com.pragma.powerup.infrastructure.restaurant.input.rest;

import com.pragma.powerup.application.restaurant.dto.request.RestaurantRequestDto;
import com.pragma.powerup.application.restaurant.dto.response.RestaurantListResponseDto;
import com.pragma.powerup.application.restaurant.handler.IRestaurantHandler;
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
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantRestController {

    private final IRestaurantHandler restaurantHandler;


    @Operation(summary = "Add a new restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "restaurant created", content = @Content),
            @ApiResponse(responseCode = "401", description = "Permission denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bad Request (e.g., The value is empty," +
                    " The length value is invalid, The number format is invalid)"
                    , content = @Content),
            @ApiResponse(responseCode = "409", description = "Is already exists", content = @Content)
    })
    @RolesAllowed("ROLE_Administrador")
    @PostMapping("/")
    public ResponseEntity<Void> saveRestaurant(@RequestBody RestaurantRequestDto restaurantRequestDto) {
        restaurantHandler.saveRestaurant(restaurantRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "list paginated restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "category returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantListResponseDto.class)))),
            @ApiResponse(responseCode = "401", description = "Permission denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bad Request (e.g., The value is empty," +
                    " The length value is invalid, The number format is invalid)"
                    , content = @Content),
            @ApiResponse(responseCode = "409", description = "Is already exists", content = @Content)
    })
    @RolesAllowed("ROLE_Cliente")
    @GetMapping("")
    public ResponseEntity<List<RestaurantListResponseDto>> listPaginatedRestaurants(@RequestParam(name = "page", defaultValue = "1") Integer numberElementsPerPage,
                                                                                    @RequestParam(name = "size", defaultValue = "1") Integer size) {
        return ResponseEntity.ok(restaurantHandler.listPaginatedRestaurants(numberElementsPerPage, size));
    }

}
