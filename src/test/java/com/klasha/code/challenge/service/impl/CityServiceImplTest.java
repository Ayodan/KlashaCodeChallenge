package com.klasha.code.challenge.service.impl;

import com.klasha.code.challenge.dto.CityDTO;
import com.klasha.code.challenge.dto.CountryStatesCitiesDTO;
import com.klasha.code.challenge.exception.BadRequestException;
import com.klasha.code.challenge.exception.InternalServerException;
import com.klasha.code.challenge.generic_response.ApiResponse;
import com.klasha.code.challenge.model.City;
import com.klasha.code.challenge.repository.CityRepository;
import com.klasha.code.challenge.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CityServiceImplTest {

    @Mock
    private CityRepository cityRepository;

    private CityService cityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cityService = new CityServiceImpl(cityRepository);
    }

    @Test
    void testGetTopCitiesByPopulation_SuccessfulFetch_ReturnsApiResponseWithCities() {
        int numberOfCities = 3;
        List<String> countries = Arrays.asList("Italy", "New Zealand", "Ghana");

        List<City> cities = new ArrayList<>();

        City city1 = new City();
        city1.setName("City1");
        city1.setPopulation(1000000);

        City city2 = new City();
        city2.setName("City2");
        city2.setPopulation(2000000);

        cities.add(city1);
        cities.add(city2);

        when(cityRepository.findTopCitiesByPopulationInCountries(numberOfCities, countries)).thenReturn(cities);

        ApiResponse<List<City>> response = cityService.getTopCitiesByPopulation(numberOfCities);

        assertNotNull(response);
        assertFalse(response.isError());
        assertEquals("All cities fetched successfully", response.getMsg());
        assertNotNull(response.getData());
        assertEquals(cities, response.getData());

        verify(cityRepository).findTopCitiesByPopulationInCountries(numberOfCities, countries);
    }

    @Test
    void testGetTopCitiesByPopulation_InvalidNumberOfCities_ThrowsBadRequestException() {
        int numberOfCities = 0;

        assertThrows(BadRequestException.class, () -> {
            cityService.getTopCitiesByPopulation(numberOfCities);
        });

        verifyNoInteractions(cityRepository);
    }

    @Test
    void testGetTopCitiesByPopulation_ExceptionInRepository_ThrowsInternalServerException() {
        int numberOfCities = 3;
        List<String> countries = Arrays.asList("Italy", "New Zealand", "Ghana");

        when(cityRepository.findTopCitiesByPopulationInCountries(numberOfCities, countries)).thenThrow(new RuntimeException());

        assertThrows(InternalServerException.class, () -> {
            cityService.getTopCitiesByPopulation(numberOfCities);
        });

        verify(cityRepository).findTopCitiesByPopulationInCountries(numberOfCities, countries);
    }

    @Test
    void testGetStatesAndCitiesByCountry_SuccessfulFetch_ReturnsApiResponseWithStatesAndCities() {
        String countryName = "TestCountry";

        CityDTO city1 = new CityDTO();
        city1.setName("City1");

        CityDTO city2 = new CityDTO();
        city2.setName("City2");

        CityDTO city3 = new CityDTO();
        city3.setName("City3");

        CityDTO city4 = new CityDTO();
        city4.setName("City4");

        List<CountryStatesCitiesDTO> statesAndCities = new ArrayList<>();
        statesAndCities.add(new CountryStatesCitiesDTO("State1", Arrays.asList(city1, city2)));
        statesAndCities.add(new CountryStatesCitiesDTO("State2", Arrays.asList(city3, city4)));

        when(cityRepository.getStatesAndCitiesByCountry(countryName)).thenReturn(statesAndCities);

        ApiResponse<List<CountryStatesCitiesDTO>> response = cityService.getStatesAndCitiesByCountry(countryName);

        assertNotNull(response);
        assertFalse(response.isError());
        assertEquals("All countries and cities fetched successfully", response.getMsg());
        assertNotNull(response.getData());
        assertEquals(statesAndCities, response.getData());

        verify(cityRepository).getStatesAndCitiesByCountry(countryName);
    }

    @Test
    void testGetStatesAndCitiesByCountry_InvalidCountryName_ThrowsBadRequestException() {
        String countryName = null;

        assertThrows(BadRequestException.class, () -> {
            cityService.getStatesAndCitiesByCountry(countryName);
        });

        verifyNoInteractions(cityRepository);
    }

    @Test
    void testGetStatesAndCitiesByCountry_ExceptionInRepository_ThrowsInternalServerException() {
        String countryName = "TestCountry";

        when(cityRepository.getStatesAndCitiesByCountry(countryName)).thenThrow(new RuntimeException());

        assertThrows(InternalServerException.class, () -> {
            cityService.getStatesAndCitiesByCountry(countryName);
        });

        verify(cityRepository, times(1)).getStatesAndCitiesByCountry(countryName);
    }

}

