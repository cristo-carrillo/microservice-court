package com.pragma.powerup.application.plate.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlateListResponseDto {
    private String nameCategory;
    private List<Plate> plate;
}
