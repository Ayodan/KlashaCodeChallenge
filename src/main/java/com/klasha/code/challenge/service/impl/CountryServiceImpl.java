package com.klasha.code.challenge.service.impl;

import com.klasha.code.challenge.dto.CountryDTO;
import com.klasha.code.challenge.exception.BadRequestException;
import com.klasha.code.challenge.exception.InternalServerException;
import com.klasha.code.challenge.exception.NotFoundException;
import com.klasha.code.challenge.generic_response.ApiResponse;
import com.klasha.code.challenge.mapper.CountryResponseMapper;
import com.klasha.code.challenge.model.Country;
import com.klasha.code.challenge.repository.CountryRepository;
import com.klasha.code.challenge.service.CountryService;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public ApiResponse<CountryDTO> getCountryInfo(String countryName) {

        if(countryName == null || countryName.isEmpty()){
            throw new BadRequestException("INVALID_REQUEST", "country name is required");
        }
        Country country = null;

        try {
            country = countryRepository.findByName(countryName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerException("GENERIC_ERROR", "something bad happened");
        }

        if (country == null) {
            throw new NotFoundException("INVALID_COUNTRY", "country does not exist");
        }
        return CountryResponseMapper.mapCountryResponse(country);
    }
}

