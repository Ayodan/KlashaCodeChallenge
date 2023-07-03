package com.klasha.code.challenge.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "conversion_rate")
public class ConversionRate {
    @Id
    private Long id;

    private String sourceCurrency;

    private String targetCurrency;

    private double rate;

}

