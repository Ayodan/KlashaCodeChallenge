package com.klasha.code.challenge.service.impl;

import com.klasha.code.challenge.dto.CurrencyConversionResponseDTO;
import com.klasha.code.challenge.exception.BadRequestException;
import com.klasha.code.challenge.generic_response.ApiResponse;
import com.klasha.code.challenge.model.ConversionRate;
import com.klasha.code.challenge.model.Country;
import com.klasha.code.challenge.repository.ConversionRateRepository;
import com.klasha.code.challenge.repository.CountryRepository;
import com.klasha.code.challenge.service.CurrencyConversionService;
import org.springframework.stereotype.Service;


@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService {
    private final ConversionRateRepository conversionRateRepository;
    private final CountryRepository countryRepository;

    public CurrencyConversionServiceImpl(ConversionRateRepository conversionRateRepository, CountryRepository countryRepository) {
        this.conversionRateRepository = conversionRateRepository;
        this.countryRepository = countryRepository;
    }

    public ApiResponse<CurrencyConversionResponseDTO> convertCurrency(String countryName, double amount, String targetCurrency) {
        if (countryName == null || countryName.isEmpty()) {
            throw new BadRequestException("INVALID_REQUEST", "country name is required");
        }

        if (amount <= 0) {
            throw new BadRequestException("INVALID_REQUEST", "Amount must be greater than 0");
        }

        if (targetCurrency == null || targetCurrency.isEmpty()) {
            throw new BadRequestException("INVALID_REQUEST", "target currency is required");
        }
        final String countryCurrency = getCountryCurrency(countryName);
        ConversionRate conversionRate = conversionRateRepository.findBySourceCurrencyAndTargetCurrency(countryCurrency, targetCurrency);
        double rate = conversionRate.getRate();
        double convertedAmount = amount * rate;

        // Format the converted amount to two decimal places
        String formattedAmount = String.format("%.2f", convertedAmount);

        final double finalAmount = Double.parseDouble(formattedAmount);
        CurrencyConversionResponseDTO currencyConversionResponseDTO = CurrencyConversionResponseDTO.builder()
                .sourceCurrency(countryCurrency)
                .rate(finalAmount)
                .build();

        ApiResponse<CurrencyConversionResponseDTO> apiResponse = new ApiResponse<>();
        apiResponse.setData(currencyConversionResponseDTO);
        apiResponse.setMsg("Currency conversion successful");
        apiResponse.setError(false);

        return apiResponse;
    }

    private String getCountryCurrency(String countryName) {
        Country countryInfo = countryRepository.findByName(countryName);
        if (countryInfo == null || countryInfo.getCurrency() == null) {
            throw new BadRequestException("INVALID_REQUEST", "Country currency not found");
        }

        return countryInfo.getCurrency();
    }
}
