package com.pritamprasad.covid_data_provider.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.pritamprasad.covid_data_provider.util.UserDefinedProperties;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;

import static java.lang.String.valueOf;

@Component
public class Covid19IndiaApiHandler {

    private Logger logger = LoggerFactory.getLogger(Covid19IndiaApiHandler.class);

    private RestTemplate restTemplate;

    private UserDefinedProperties properties;

    private Gson gson;

    @Autowired
    public Covid19IndiaApiHandler(RestTemplate restTemplate, UserDefinedProperties properties, Gson gson) {
        this.restTemplate = restTemplate;
        this.properties = properties;
        this.gson = gson;
    }

    public List<LatestLogEndpointResponse> getLatestLog() {
        LatestLogEndpointResponse[] endpointResponses = restTemplate.getForObject(properties.getLatestLogUrl(), LatestLogEndpointResponse[].class);
        return endpointResponses == null ? Collections.emptyList() : Arrays.asList(endpointResponses);
    }

//    public static void main(String[] args) {
//        UserDefinedProperties properties = new UserDefinedProperties();
//        properties.setDatewiseSummaryUrl("https://api.covid19india.org/v4/min/data-YYYY-MM-DD.min.json");
//        Covid19IndiaApiHandler handler = new Covid19IndiaApiHandler(new RestTemplateBuilder().build(), properties, new GsonBuilder().setPrettyPrinting().create());
//        handler.getSummaryForDate(LocalDate.now().minusDays(1));
//    }

    public DatewiseIndiaCovidApiResponse getSummaryForDate(final LocalDate date) {
        DatewiseIndiaCovidApiResponse apiResponse = new DatewiseIndiaCovidApiResponse();
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(
                    constructUrl(valueOf(date.getYear()), valueOf(date.getMonthValue()), valueOf(date.getDayOfMonth())),
                    String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                apiResponse = parseResponseForSummaryDataByDate(responseEntity.getBody());
                apiResponse.setDateStamp(date);
            } else {
                logger.warn("Response not obtained, check constructed url..");
            }
        } catch (URISyntaxException e) {
            logger.warn("Url construction failed : {}", e.getMessage());
        }
        return apiResponse;
    }

    private DatewiseIndiaCovidApiResponse parseResponseForSummaryDataByDate(final String responseString) {
        DatewiseIndiaCovidApiResponse apiResponse = new DatewiseIndiaCovidApiResponse();
        Map stateMap = gson.fromJson(responseString, Map.class);
        stateMap.forEach((key, value) -> {
                    DatewiseIndiaCovidApiResponse.State state = gson.fromJson(gson.toJson(value), DatewiseIndiaCovidApiResponse.State.class);
                    apiResponse.getStateMap().put(key.toString(), state);
                }
        );
        return apiResponse;
    }

    private URI constructUrl(@NonNull String year, @NonNull String month, @NonNull String date) throws URISyntaxException {
        String uriString = properties.getDatewiseSummaryUrl()
                .replace("YYYY", year)
                .replace("MM", month)
                .replace("DD", date);
        logger.debug("Constructed uri : {}", uriString);
        return new URI(uriString);
    }


}
