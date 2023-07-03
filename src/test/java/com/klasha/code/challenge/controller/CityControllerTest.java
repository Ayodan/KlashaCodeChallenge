package com.klasha.code.challenge.controller;

import com.klasha.code.challenge.dto.CityDTO;
import com.klasha.code.challenge.dto.CountryStatesCitiesDTO;
import com.klasha.code.challenge.generic_response.ApiResponse;
import com.klasha.code.challenge.model.City;
import com.klasha.code.challenge.service.impl.CityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CityControllerTest {

    @Mock
    private CityServiceImpl cityService;

    private MockMvc mockMvc;
    private CityController cityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cityController = new CityController(cityService);
        mockMvc = MockMvcBuilders.standaloneSetup(cityController).build();
    }

    @Test
    void testGetTopCities_SuccessfulFetch_ReturnsApiResponseWithCities() throws Exception {
        int numberOfCities = 5;
        City city1 = new City();
        city1.setName("City1");
        city1.setPopulation(1000000);

        City city2 = new City();
        city2.setName("City2");
        city2.setPopulation(2000000);

        List<City> cities = Arrays.asList(city1, city2);
        ApiResponse<List<City>> expectedResponse = new ApiResponse<>();
        expectedResponse.setError(false);
        expectedResponse.setMsg("All cities fetched successfully");
        expectedResponse.setData(cities);

        when(cityService.getTopCitiesByPopulation(numberOfCities)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/top-cities/{number_of_cities}", numberOfCities))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value(false))
                .andExpect(jsonPath("$.msg").value("All cities fetched successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(cities.size()));
    }

    @Test
    void testGetStatesAndCitiesByCountry_SuccessfulFetch_ReturnsApiResponseWithStatesAndCities() throws Exception {
        String countryName = "TestCountry";

        CityDTO city1 = new CityDTO();
        city1.setName("City1");

        CityDTO city2 = new CityDTO();
        city2.setName("City2");

        CityDTO city3 = new CityDTO();
        city3.setName("City3");

        CityDTO city4 = new CityDTO();
        city4.setName("City4");
        List<CountryStatesCitiesDTO> statesAndCities = Arrays.asList(
                new CountryStatesCitiesDTO("State 1", Arrays.asList(city1, city2)),
                new CountryStatesCitiesDTO("State 2", Arrays.asList(city3, city4))
        );
        ApiResponse<List<CountryStatesCitiesDTO>> expectedResponse = new ApiResponse<>();
        expectedResponse.setError(false);
        expectedResponse.setMsg("All countries and cities fetched successfully");
        expectedResponse.setData(statesAndCities);

        when(cityService.getStatesAndCitiesByCountry(countryName)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/states-cities")
                .param("country_name", countryName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value(false))
                .andExpect(jsonPath("$.msg").value("All countries and cities fetched successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(statesAndCities.size()));
    }

}

