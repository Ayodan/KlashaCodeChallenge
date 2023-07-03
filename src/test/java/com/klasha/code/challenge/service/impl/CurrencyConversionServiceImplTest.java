package com.klasha.code.challenge.service.impl;

import com.klasha.code.challenge.dto.CurrencyConversionResponseDTO;
import com.klasha.code.challenge.exception.BadRequestException;
import com.klasha.code.challenge.generic_response.ApiResponse;
import com.klasha.code.challenge.model.ConversionRate;
import com.klasha.code.challenge.model.Country;
import com.klasha.code.challenge.repository.ConversionRateRepository;
import com.klasha.code.challenge.repository.CountryRepository;
import com.klasha.code.challenge.service.CurrencyConversionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CurrencyConversionServiceImplTest {

    @Mock
    private ConversionRateRepository conversionRateRepository;

    @Mock
    private CountryRepository countryRepository;

    private CurrencyConversionService currencyConversionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        currencyConversionService = new CurrencyConversionServiceImpl(conversionRateRepository, countryRepository);
    }

    @Test
    void testConvertCurrency_SuccessfulConversion_ReturnsApiResponse() {
        String countryName = "TestCountry";
        double amount = 100;
        String targetCurrency = "USD";
        String countryCurrency = "NGN";
        double rate = 0.01;
        double convertedAmount = amount * rate;

        ConversionRate conversionRate = new ConversionRate();
        conversionRate.setSourceCurrency(countryCurrency);
        conversionRate.setTargetCurrency(targetCurrency);
        conversionRate.setRate(rate);

        Country country = new Country();
        country.setName(countryName);
        country.setCurrency(countryCurrency);

        when(countryRepository.findByName(countryName)).thenReturn(country);
        when(conversionRateRepository.findBySourceCurrencyAndTargetCurrency(countryCurrency, targetCurrency))
                .thenReturn(conversionRate);

        ApiResponse<CurrencyConversionResponseDTO> response = currencyConversionService.convertCurrency(countryName, amount, targetCurrency);

        assertNotNull(response);
        assertFalse(response.isError());
        assertEquals("Currency conversion successful", response.getMsg());
        assertNotNull(response.getData());

        CurrencyConversionResponseDTO conversionResponseDTO = response.getData();
        assertEquals(countryCurrency, conversionResponseDTO.getSourceCurrency());
        assertEquals(convertedAmount, conversionResponseDTO.getRate());

        verify(countryRepository).findByName(countryName);
        verify(conversionRateRepository).findBySourceCurrencyAndTargetCurrency(countryCurrency, targetCurrency);
    }

    @Test
    void testConvertCurrency_InvalidCountryName_ThrowsBadRequestException() {
        String countryName = null;
        double amount = 100;
        String targetCurrency = "USD";

        assertThrows(BadRequestException.class, () -> {
            currencyConversionService.convertCurrency(countryName, amount, targetCurrency);
        });

        verifyNoInteractions(countryRepository);
        verifyNoInteractions(conversionRateRepository);
    }
}

