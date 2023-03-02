package com.pragma.powerup.application.plate.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Plate {
    private String name;
    private String description;
    private Long price;
    private String urlPicture;
}
