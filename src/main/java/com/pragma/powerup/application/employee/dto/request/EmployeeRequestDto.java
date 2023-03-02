package com.pragma.powerup.application.employee.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EmployeeRequestDto {
    private Long idRestaurant;
    private Long idPeople;
}
