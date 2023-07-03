package com.klasha.code.challenge.controller;

import com.klasha.code.challenge.dto.CurrencyConversionResponseDTO;
import com.klasha.code.challenge.generic_response.ApiResponse;
import com.klasha.code.challenge.service.impl.CurrencyConversionServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConversionController {
    private final CurrencyConversionServiceImpl conversionService;

    public CurrencyConversionController(CurrencyConversionServiceImpl conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping("/convert-currency")
    public ApiResponse<CurrencyConversionResponseDTO> convertCurrency(
            @RequestParam("country") String countryName,
            @RequestParam("amount") double amount,
            @RequestParam("target_currency") String targetCurrency) {
        return conversionService.convertCurrency(countryName, amount, targetCurrency);

    }
}

