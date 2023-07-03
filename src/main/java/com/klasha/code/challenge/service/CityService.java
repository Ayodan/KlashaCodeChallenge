package com.klasha.code.challenge.service;

import com.klasha.code.challenge.dto.CountryStatesCitiesDTO;
import com.klasha.code.challenge.generic_response.ApiResponse;
import com.klasha.code.challenge.model.City;

import java.util.List;

public interface CityService {
    ApiResponse<List<City>> getTopCitiesByPopulation(int numberOfCities);
    ApiResponse<List<CountryStatesCitiesDTO>> getStatesAndCitiesByCountry(String countryName);
}
