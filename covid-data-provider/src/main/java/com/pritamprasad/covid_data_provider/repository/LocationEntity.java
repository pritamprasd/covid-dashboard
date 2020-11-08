package com.pritamprasad.covid_data_provider.repository;

import com.pritamprasad.covid_data_provider.models.EntityType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "location")
@Getter
@Setter
public class LocationEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    private String code;

    private Long population;

    private Long parentId = 0L;

    private EntityType type;

    private LocalDate createdDate;
}
