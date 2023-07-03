package com.klasha.code.challenge.service.impl;

import com.klasha.code.challenge.dto.CountryStatesCitiesDTO;
import com.klasha.code.challenge.exception.BadRequestException;
import com.klasha.code.challenge.exception.InternalServerException;
import com.klasha.code.challenge.generic_response.ApiResponse;
import com.klasha.code.challenge.model.City;
import com.klasha.code.challenge.repository.CityRepository;
import com.klasha.code.challenge.service.CityService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public ApiResponse<List<City>> getTopCitiesByPopulation(int numberOfCities) {
        if(numberOfCities <= 0){
            throw new BadRequestException("INVALID_REQUEST", "number of cities must be greater than 0");
        }
        List<String> countries = Arrays.asList("Italy", "New Zealand", "Ghana");

        List<City> topCitiesByPopulationInCountries = null;
        try {
            topCitiesByPopulationInCountries = cityRepository.findTopCitiesByPopulationInCountries(numberOfCities, countries);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerException("GENERIC_ERROR", "something bad happened");
        }

        ApiResponse<List<City>> response = new ApiResponse<>();
        response.setError(false);
        response.setMsg("All cities fetched successfully");
        response.setData(topCitiesByPopulationInCountries);

        return response;

    }

    public ApiResponse<List<CountryStatesCitiesDTO>> getStatesAndCitiesByCountry(String countryName) {
        if(countryName == null || countryName.isEmpty()){
            throw new BadRequestException("INVALID_REQUEST", "country name is required");
        }

        List<CountryStatesCitiesDTO> statesAndCitiesByCountry = null;
        try {
            statesAndCitiesByCountry = cityRepository.getStatesAndCitiesByCountry(countryName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerException("GENERIC_ERROR", "something bad happened");
        }

        ApiResponse<List<CountryStatesCitiesDTO>> response = new ApiResponse<>();
        response.setError(false);
        response.setMsg("All countries and cities fetched successfully");
        response.setData(statesAndCitiesByCountry);

        return response;
    }

}
