package com.pritamprasad.covid_data_provider.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "location",
        uniqueConstraints = @UniqueConstraint(columnNames = {"code", "parentId"})
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String code;

    private Long population;

    private Long parentId;

    private EntityType type;

    private LocalDate createdDate;
}
