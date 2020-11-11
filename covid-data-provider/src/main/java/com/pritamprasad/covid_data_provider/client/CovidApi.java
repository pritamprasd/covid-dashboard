package com.pritamprasad.covid_data_provider.client;

import com.pritamprasad.covid_data_provider.models.DatewiseIndiaCovidApiResponse;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Base Interface for polling data from various sources
 */
public interface CovidApi {
    /**
     * Gets covid data response for provided date
     * @param date input date
     * @return response parsed into DatewiseIndiaCovidApiResponse object
     */
    Optional<DatewiseIndiaCovidApiResponse> getCovidApiResponseForDate(LocalDate date);
}
