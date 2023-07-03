package com.klasha.code.challenge.repository;

import com.klasha.code.challenge.dto.CountryStatesCitiesDTO;
import com.klasha.code.challenge.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query(value = "SELECT c.* FROM city c INNER JOIN state s ON c.state_id = s.id INNER JOIN country co ON s.country_id = co.id WHERE co.name IN :countries ORDER BY c.population DESC LIMIT :numberOfCities", nativeQuery = true)
    List<City> findTopCitiesByPopulationInCountries(@Param("numberOfCities") int numberOfCities, @Param("countries") List<String> countries);

    @Query(value = "SELECT c.* FROM city c " +
            "JOIN state s ON c.state_id = s.id " +
            "JOIN country co ON s.country_id = co.id " +
            "WHERE co.name = :countryName " +
            "ORDER BY s.name, c.name", nativeQuery = true)
    List<CountryStatesCitiesDTO> getStatesAndCitiesByCountry(@Param("countryName") String countryName);
}
