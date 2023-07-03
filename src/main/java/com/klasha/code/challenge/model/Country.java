package com.klasha.code.challenge.model;

import javax.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int population;

    @Column(name = "capital_city")
    private String capitalCity;

    private String location;

    private String currency;

    private String iso2;

    private String iso3;


}

