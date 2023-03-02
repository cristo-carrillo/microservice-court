package com.pragma.powerup.infrastructure.employee.input.rest;

import com.pragma.powerup.application.employee.dto.request.UserEmployeeRequestDto;
import com.pragma.powerup.application.employee.handler.IUserEmployeeHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeRestController {

    private final IUserEmployeeHandler userEmployeeHandler;
    @RolesAllowed("ROLE_Propietario")
    @Operation(summary = "Add a new employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "restaurant created", content = @Content),
            @ApiResponse(responseCode = "401", description = "Permission denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bad Request (e.g., The value is empty," +
                    " The length value is invalid, The number format is invalid)"
                    , content = @Content),
            @ApiResponse(responseCode = "409", description = "Is already exists", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void>  saveEmployee(@RequestBody UserEmployeeRequestDto userEmployeeRequestDto){
        userEmployeeHandler.saveEmployee(userEmployeeRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
