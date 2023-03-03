package com.pragma.powerup.infrastructure.plate.input.rest;

import com.pragma.powerup.application.plate.dto.request.PlateRequestDto;
import com.pragma.powerup.application.plate.dto.request.PlateUpdateRequestDto;
import com.pragma.powerup.application.plate.dto.response.PlateListResponseDto;
import com.pragma.powerup.application.plate.dto.response.PlateResponseDto;
import com.pragma.powerup.application.plate.handler.IPlateHandler;
import com.pragma.powerup.application.restaurant.dto.response.RestaurantListResponseDto;
import com.pragma.powerup.domain.plate.spi.IPlatePersistencePort;
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
@RequestMapping("/api/v1/plate")
@RequiredArgsConstructor
public class PlateRestController {

    private final IPlateHandler plateHandler;
    private final IPlatePersistencePort plateRepository;

    @RolesAllowed("ROLE_Propietario")
    @Operation(summary = "Add a new Plate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plate created", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bad Request (e.g., The number format is invalid)"
                    , content = @Content),
            @ApiResponse(responseCode = "409", description = "Plate already exists", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> savePlate(@RequestBody PlateRequestDto plateRequestDto) {
        plateHandler.savePlate(plateRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RolesAllowed("ROLE_Propietario")
    @Operation(summary = "update a Plate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "plate returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlateResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Plate not exist", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PlateResponseDto> updatePlate(@PathVariable Long id, @RequestBody PlateUpdateRequestDto plateUpdateRequestDto) {
        return ResponseEntity.ok(plateHandler.updatePlate(id, plateUpdateRequestDto));
    }

    @RolesAllowed("ROLE_Propietario")
    @Operation(summary = "update status Plate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "plate returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlateResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Plate not exist", content = @Content)
    })
    @PutMapping("/{id}/{status}")
    public ResponseEntity<PlateResponseDto> updateStatusPLate(@PathVariable Long id,
                                                              @PathVariable Boolean status) {
        return ResponseEntity.ok(plateHandler.updateStatusPLate(id, status));
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
    public ResponseEntity<List<PlateListResponseDto>> listPaginatedPlates(@RequestParam(name = "id_restaurant") Long idRestaurant,
                                                                          @RequestParam(name = "page", defaultValue = "1") Integer numberElementsPerPage,
                                                                          @RequestParam(name = "size", defaultValue = "1") Integer size) {
        return ResponseEntity.ok(plateHandler.listPaginatedPlates(idRestaurant,numberElementsPerPage, size));
    }

}
