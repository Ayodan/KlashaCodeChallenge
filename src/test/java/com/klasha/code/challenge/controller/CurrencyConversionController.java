package com.klasha.code.challenge.controller;

import com.klasha.code.challenge.dto.CurrencyConversionResponseDTO;
import com.klasha.code.challenge.generic_response.ApiResponse;
import com.klasha.code.challenge.service.impl.CurrencyConversionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CurrencyConversionControllerTest {

    @Mock
    private CurrencyConversionServiceImpl conversionService;

    private MockMvc mockMvc;
    private CurrencyConversionController conversionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        conversionController = new CurrencyConversionController(conversionService);
        mockMvc = MockMvcBuilders.standaloneSetup(conversionController).build();
    }

    @Test
    void testConvertCurrency_SuccessfulConversion_ReturnsApiResponseWithConvertedAmount() throws Exception {

        String countryName = "TestCountry";
        double amount = 100.0;
        String targetCurrency = "USD";
        CurrencyConversionResponseDTO responseDTO = new CurrencyConversionResponseDTO("NGN", 500.0);
        ApiResponse<CurrencyConversionResponseDTO> expectedResponse = new ApiResponse<>();
        expectedResponse.setError(false);
        expectedResponse.setMsg("Currency conversion successful");
        expectedResponse.setData(responseDTO);

        when(conversionService.convertCurrency(eq(countryName), eq(amount), eq(targetCurrency)))
                .thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/convert-currency")
                .param("country", countryName)
                .param("amount", String.valueOf(amount))
                .param("target_currency", targetCurrency))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value(false))
                .andExpect(jsonPath("$.msg").value("Currency conversion successful"))
                .andExpect(jsonPath("$.data.sourceCurrency").value(responseDTO.getSourceCurrency()))
                .andExpect(jsonPath("$.data.rate").value(responseDTO.getRate()));
    }


}

