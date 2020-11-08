package com.pritamprasad.covid_data_provider.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class DateRangeResponse {
    private LocalDate maxDate;
    private LocalDate minDate;
}
