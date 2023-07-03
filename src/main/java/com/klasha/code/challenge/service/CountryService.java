package com.klasha.code.challenge.service;

import com.klasha.code.challenge.dto.CountryDTO;
import com.klasha.code.challenge.generic_response.ApiResponse;

public interface CountryService {
    ApiResponse<CountryDTO> getCountryInfo(String countryName);
}
