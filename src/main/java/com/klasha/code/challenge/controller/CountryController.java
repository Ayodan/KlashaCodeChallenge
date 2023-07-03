package com.klasha.code.challenge.controller;

import com.klasha.code.challenge.dto.CountryDTO;
import com.klasha.code.challenge.generic_response.ApiResponse;
import com.klasha.code.challenge.service.impl.CountryServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {
    private final CountryServiceImpl countryServiceImpl;

    public CountryController(CountryServiceImpl countryServiceImpl) {
        this.countryServiceImpl = countryServiceImpl;
    }

    @GetMapping("/country-info")
    public ApiResponse<CountryDTO> getCountryInfo(
            @RequestParam("country") String countryName) {
        return countryServiceImpl.getCountryInfo(countryName);
    }

}

