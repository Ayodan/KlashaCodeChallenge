package com.klasha.code.challenge.service.impl;

import com.klasha.code.challenge.dto.CityDTO;
import com.klasha.code.challenge.dto.CountryStatesCitiesDTO;
import com.klasha.code.challenge.exception.BadRequestException;
import com.klasha.code.challenge.exception.InternalServerException;
import com.klasha.code.challenge.generic_response.ApiResponse;
import com.klasha.code.challenge.model.City;
import com.klasha.code.challenge.repository.CityRepository;
import com.klasha.code.challenge.service.CityService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        if (countryName == null || countryName.isEmpty()) {
            throw new BadRequestException("INVALID_REQUEST", "Country name is required");
        }

        List<Object[]> results = cityRepository.getStatesAndCitiesByCountry(countryName);

        Map<String, List<CityDTO>> citiesByState = results.stream()
                .collect(Collectors.groupingBy(
                        result -> (String) result[0],  // Group by state name
                        Collectors.mapping(
                                result -> {
                                    CityDTO city = new CityDTO();
                                    city.setName((String) result[1]);
                                    return city;
                                },
                                Collectors.toList()
                        )
                ));

        List<CountryStatesCitiesDTO> statesAndCities = citiesByState.entrySet().stream()
                .map(entry -> {
                    CountryStatesCitiesDTO dto = new CountryStatesCitiesDTO();
                    dto.setStateName(entry.getKey());
                    dto.setCities(entry.getValue());
                    return dto;
                })
                .collect(Collectors.toList());

        ApiResponse<List<CountryStatesCitiesDTO>> response = new ApiResponse<>();
        response.setError(false);
        response.setMsg("All countries and cities fetched successfully");
        response.setData(statesAndCities);

        return response;
    }


}
