package com.klasha.code.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CurrencyConversionResponseDTO {
    private String sourceCurrency;
    private double rate;
}
