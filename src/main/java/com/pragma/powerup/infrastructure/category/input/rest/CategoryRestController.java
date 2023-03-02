package com.pragma.powerup.infrastructure.category.input.rest;

import com.pragma.powerup.application.category.dto.request.CategoryRequestDto;
import com.pragma.powerup.application.category.dto.response.CategoryResponseDto;
import com.pragma.powerup.application.category.handler.ICategoryHandler;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryRestController {

    private final ICategoryHandler categoryHandler;
    @Operation(summary = "Add a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "category created", content = @Content),
            @ApiResponse(responseCode = "409", description = "category already exists", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> saveCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        categoryHandler.saveCategory(categoryRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @Operation(summary = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All categories returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CategoryResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories(){
        return ResponseEntity.ok(categoryHandler.getAllCategorys());
    }
    @Operation(summary = "Get category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "category returned",
                    content = @Content(mediaType = "application/json",
                           schema = @Schema(implementation = CategoryResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategory(@RequestParam Long id){
        return ResponseEntity.ok(categoryHandler.getCategory(id));
    }
}
