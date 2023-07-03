package com.klasha.code.challenge.service.impl;


import com.klasha.code.challenge.dto.CountryDTO;
import com.klasha.code.challenge.exception.BadRequestException;
import com.klasha.code.challenge.exception.InternalServerException;
import com.klasha.code.challenge.exception.NotFoundException;
import com.klasha.code.challenge.generic_response.ApiResponse;
import com.klasha.code.challenge.model.Country;
import com.klasha.code.challenge.repository.CountryRepository;
import com.klasha.code.challenge.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        countryService = new CountryServiceImpl(countryRepository);
    }

    @Test
    void testGetCountryInfo_SuccessfulFetch_ReturnsApiResponseWithCountryInfo() {
        String countryName = "TestCountry";
        Country country = new Country();
        country.setName(countryName);
        country.setPopulation(1000000);
        country.setCurrency("TestCurrency");

        when(countryRepository.findByName(countryName)).thenReturn(country);

        ApiResponse<CountryDTO> response = countryService.getCountryInfo(countryName);

        assertNotNull(response);
        assertFalse(response.isError());
        assertNotNull(response.getMsg());

        CountryDTO countryDTO = response.getData();
        assertNotNull(countryDTO);
        assertEquals(1000000, countryDTO.getPopulation());
        assertEquals("TestCurrency", countryDTO.getCurrency());

        verify(countryRepository).findByName(countryName);
    }

    @Test
    void testGetCountryInfo_InvalidCountryName_ThrowsBadRequestException() {
        String countryName = null;

        assertThrows(BadRequestException.class, () -> {
            countryService.getCountryInfo(countryName);
        });

        verifyNoInteractions(countryRepository);
    }

    @Test
    void testGetCountryInfo_CountryNotFound_ThrowsNotFoundException() {
        String countryName = "NonExistentCountry";

        when(countryRepository.findByName(countryName)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> {
            countryService.getCountryInfo(countryName);
        });

        verify(countryRepository, times(1)).findByName(countryName);
    }

    @Test
    void testGetCountryInfo_ExceptionInRepository_ThrowsInternalServerException() {
        String countryName = "TestCountry";

        when(countryRepository.findByName(countryName)).thenThrow(new RuntimeException());

        assertThrows(InternalServerException.class, () -> {
            countryService.getCountryInfo(countryName);
        });

        verify(countryRepository).findByName(countryName);
    }


}
