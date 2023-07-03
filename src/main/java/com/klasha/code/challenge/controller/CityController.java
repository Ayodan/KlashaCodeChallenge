package com.klasha.code.challenge.controller;

import com.klasha.code.challenge.dto.CountryStatesCitiesDTO;
import com.klasha.code.challenge.generic_response.ApiResponse;
import com.klasha.code.challenge.model.City;
import com.klasha.code.challenge.service.impl.CityServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController {
    private final CityServiceImpl cityServiceImpl;

    public CityController(CityServiceImpl cityServiceImpl) {
        this.cityServiceImpl = cityServiceImpl;
    }

    @GetMapping("/top-cities/{number_of_cities}")
    public ApiResponse<List<City>> getTopCities(@PathVariable ("number_of_cities") int numberOfCities) {
        return cityServiceImpl.getTopCitiesByPopulation(numberOfCities);
    }

    @GetMapping("/states-cities")
    public ApiResponse<List<CountryStatesCitiesDTO>> getStatesAndCitiesByCountry( @RequestParam("country_name") String countryName) {
        return cityServiceImpl.getStatesAndCitiesByCountry(countryName);
    }


}
