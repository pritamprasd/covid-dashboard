package com.pritamprasad.covid_data_provider.repository;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "metadata")
@Setter
@Getter
public class MetaDataEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long confirmed;
    private Long deceased;
    private Long recovered;
    private Long tested;

    private Long entityId;

    private LocalDate createdDate;
}
