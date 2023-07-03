package com.klasha.code.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CountryStatesCitiesDTO {
    private String stateName;
    private List<CityDTO> cities;
}
