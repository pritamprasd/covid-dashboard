package com.pritamprasad.covid_data_provider.repository;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "location")
@Getter
@Setter
public class LocationEntity {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String code;

    private Long population;

    private int stateId = 0;

    private LocalDate createdDate;
}
