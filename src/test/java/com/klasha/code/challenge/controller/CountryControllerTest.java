package com.klasha.code.challenge.controller;

import com.klasha.code.challenge.dto.CountryDTO;
import com.klasha.code.challenge.generic_response.ApiResponse;
import com.klasha.code.challenge.service.impl.CountryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CountryControllerTest {

    @Mock
    private CountryServiceImpl countryService;

    private MockMvc mockMvc;
    private CountryController countryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        countryController = new CountryController(countryService);
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test
    void testGetCountryInfo_SuccessfulFetch_ReturnsApiResponseWithCountryInfo() throws Exception {
        String countryName = "TestCountry";

        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCapitalCity("Capital City");
        countryDTO.setPopulation(1000000);
        countryDTO.setCurrency("EUR");
        countryDTO.setIso2("PK");
        countryDTO.setIso3("PAK");

        ApiResponse<CountryDTO> expectedResponse = new ApiResponse<>();
        expectedResponse.setError(false);
        expectedResponse.setMsg("Country info fetched successfully");
        expectedResponse.setData(countryDTO);

        when(countryService.getCountryInfo(countryName)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/country-info")
                .param("country", countryName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value(false))
                .andExpect(jsonPath("$.msg").value("Country info fetched successfully"))
                .andExpect(jsonPath("$.data.capitalCity").value(countryDTO.getCapitalCity()));
    }


}

