package com.klasha.code.challenge.repository;

import com.klasha.code.challenge.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByName(String name);
}

