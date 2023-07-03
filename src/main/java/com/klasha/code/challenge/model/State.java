package com.klasha.code.challenge.model;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "state")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private String stateCode;

}
