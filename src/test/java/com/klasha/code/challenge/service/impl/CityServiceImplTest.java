package com.klasha.code.challenge.service.impl;

import com.klasha.code.challenge.dto.CountryStatesCitiesDTO;
import com.klasha.code.challenge.exception.BadRequestException;
import com.klasha.code.challenge.exception.InternalServerException;
import com.klasha.code.challenge.generic_response.ApiResponse;
import com.klasha.code.challenge.model.City;
import com.klasha.code.challenge.repository.CityRepository;
import com.klasha.code.challenge.service.CityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    void testGetStatesAndCitiesByCountry_InvalidCountryName_ThrowsBadRequestException() {
        String countryName = null;

        assertThrows(BadRequestException.class, () -> {
            cityService.getStatesAndCitiesByCountry(countryName);
        });

        verifyNoInteractions(cityRepository);
    }

    @Test
    void testGetStatesAndCitiesByCountry_Success() {
        String countryName = "Country";

        List<Object[]> mockQueryResult = new ArrayList<>();
        mockQueryResult.add(new Object[]{"State 1", "City 1"});
        mockQueryResult.add(new Object[]{"State 1", "City 2"});
        mockQueryResult.add(new Object[]{"State 2", "City 3"});

        Mockito.when(cityRepository.getStatesAndCitiesByCountry(countryName))
                .thenReturn(mockQueryResult);

        ApiResponse<List<CountryStatesCitiesDTO>> response = cityService.getStatesAndCitiesByCountry(countryName);

        Assertions.assertFalse(response.isError());
        Assertions.assertEquals("All countries and cities fetched successfully", response.getMsg());

        List<CountryStatesCitiesDTO> statesAndCities = response.getData();
        Assertions.assertEquals(2, statesAndCities.size());

        CountryStatesCitiesDTO state1DTO = statesAndCities.get(0);
        Assertions.assertEquals("State 1", state1DTO.getStateName());
        Assertions.assertEquals(2, state1DTO.getCities().size());
        CountryStatesCitiesDTO state2DTO = statesAndCities.get(1);
        Assertions.assertEquals("State 2", state2DTO.getStateName());
        Assertions.assertEquals(1, state2DTO.getCities().size());
    }

    @Test
    void testGetStatesAndCitiesByCountry_InvalidCountryName() {
        String countryName = ""; // Empty country name

        Assertions.assertThrows(BadRequestException.class, () -> {
            cityService.getStatesAndCitiesByCountry(countryName);
        });
    }

}

