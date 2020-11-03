package com.pritamprasad.covid_data_provider.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties
@Getter
@Setter
public class LocationResponse {

    private int id;

    private String name;

    private boolean isState;

    private int stateId;

    private long population;
}
