package com.klasha.code.challenge.mapper;

import com.klasha.code.challenge.dto.CountryDTO;
import com.klasha.code.challenge.generic_response.ApiResponse;
import com.klasha.code.challenge.model.Country;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CountryResponseMapper {


    public static ApiResponse<CountryDTO> mapCountryResponse(Country country) {
        final CountryDTO countryDTO = CountryDTO.builder()
                .capitalCity(country.getCapitalCity())
                .currency(country.getCurrency())
                .iso2(country.getIso2())
                .iso3(country.getIso3())
                .location(country.getLocation())
                .population(country.getPopulation())
                .build();

        ApiResponse<CountryDTO> apiResponse = new ApiResponse<>();
        apiResponse.setData(countryDTO);
        apiResponse.setError(false);
        apiResponse.setMsg("Country Successfully fetch");

        return apiResponse;
    }

}
