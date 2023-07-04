package com.klasha.code.challenge.repository;

import com.klasha.code.challenge.dto.CityDTO;
import com.klasha.code.challenge.dto.CountryStatesCitiesDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CountryStatesCitiesDTORowMapper implements RowMapper<CountryStatesCitiesDTO> {
    @Override
    public CountryStatesCitiesDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        CountryStatesCitiesDTO dto = new CountryStatesCitiesDTO();
        dto.setStateName(resultSet.getString("state_name"));
        CityDTO city = new CityDTO();
        city.setName(resultSet.getString("city_name"));
        dto.setCities(Collections.singletonList(city));
        return dto;
    }
}