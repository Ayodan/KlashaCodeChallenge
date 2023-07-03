package com.klasha.code.challenge.service;

import com.klasha.code.challenge.dto.CurrencyConversionResponseDTO;
import com.klasha.code.challenge.generic_response.ApiResponse;

public interface CurrencyConversionService {
    ApiResponse<CurrencyConversionResponseDTO> convertCurrency(String countryName, double amount, String targetCurrency);
}
