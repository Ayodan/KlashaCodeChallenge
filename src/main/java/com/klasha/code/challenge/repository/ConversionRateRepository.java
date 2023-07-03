package com.klasha.code.challenge.repository;

import com.klasha.code.challenge.model.ConversionRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionRateRepository extends JpaRepository<ConversionRate, Long> {
    ConversionRate findBySourceCurrencyAndTargetCurrency(String sourceCurrency, String targetCurrency);
}

