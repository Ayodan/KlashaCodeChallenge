package com.klasha.code.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CountryDTO {
    private int population;

    @Column(name = "capital_city")
    private String capitalCity;

    private String location;

    private String currency;

    private String iso2;

    private String iso3;
}
