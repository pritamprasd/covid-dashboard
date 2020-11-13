package com.pritamprasad.covid_data_provider.client;

import com.google.gson.Gson;
import com.pritamprasad.covid_data_provider.models.DatewiseIndiaCovidApiResponse;
import com.pritamprasad.covid_data_provider.models.DatewiseIndiaCovidApiResponse.State;
import com.pritamprasad.covid_data_provider.util.Messages;
import com.pritamprasad.covid_data_provider.util.UserDefinedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;

import static com.pritamprasad.covid_data_provider.client.ClientHelper.constructUrlForCovidindia19Api;
import static java.util.Optional.ofNullable;

/**
 * DataPulledFrom: https://www.covid19india.org/
 */
@Component
public class Covid19IndiaApiHandler implements CovidApi {

    private static final Logger logger = LoggerFactory.getLogger(Covid19IndiaApiHandler.class);

    private final RestTemplate restTemplate;

    private final UserDefinedProperties properties;

    private final Gson gson;

    @Autowired
    public Covid19IndiaApiHandler(final RestTemplate restTemplate, final UserDefinedProperties properties,
                                  final Gson gson) {
        this.restTemplate = restTemplate;
        this.properties = properties;
        this.gson = gson;
    }

    @Override
    public Optional<DatewiseIndiaCovidApiResponse> getCovidApiResponseForDate(final LocalDate date) {
        DatewiseIndiaCovidApiResponse apiResponse = null;
        ResponseEntity<String> responseEntity;
        try {
            final URI constructedUrl = constructUrlForCovidindia19Api(date, properties.getDatewiseSummaryUrl());
            responseEntity = restTemplate.getForEntity(constructedUrl, String.class);
            apiResponse = parseResponseForSummaryDataByDate(responseEntity.getBody());
            apiResponse.setDateStamp(date);
            logger.debug(Messages.RESPONSE_OBTAINED_FOR_URL, constructedUrl);
        } catch (URISyntaxException | RestClientException e) {
            logger.warn(Messages.URL_CONSTRUCTION_FAILED_WITH_MESSAGE, e.getMessage());
        }
        return ofNullable(apiResponse);
    }

    private DatewiseIndiaCovidApiResponse parseResponseForSummaryDataByDate(final String responseString) {
        final DatewiseIndiaCovidApiResponse apiResponse = new DatewiseIndiaCovidApiResponse();
        final Map<String, Object> stateMap = gson.fromJson(responseString, Map.class);
        stateMap.forEach((key, value) ->
                apiResponse.getStateMap().put(key, gson.fromJson(gson.toJson(value), State.class)));
        return apiResponse;
    }

}
