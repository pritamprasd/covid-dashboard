package com.pritamprasad.covid_data_provider.client;

import com.google.gson.Gson;
import com.pritamprasad.covid_data_provider.models.DatewiseIndiaCovidApiResponse;
import com.pritamprasad.covid_data_provider.util.UserDefinedProperties;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Optional;

import static com.pritamprasad.covid_data_provider.client.ClientHelper.constructUrlForCovidindia19Api;
import static com.pritamprasad.covid_data_provider.utils.DataProvider.getSampleResponseForCovidIndia19DateWiseApi;
import static com.pritamprasad.covid_data_provider.utils.DataProvider.getUserDefinedProperties;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.MockRestServiceServer.bindTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class Covid19IndiaApiHandlerTest {

    private MockRestServiceServer covidIndia19Api;

    private Covid19IndiaApiHandler covid19IndiaApiHandler;

    private final UserDefinedProperties properties = getUserDefinedProperties();


    @BeforeEach
    void setUp() {
        final RestTemplate restTemplate = new RestTemplateBuilder().build();
        covid19IndiaApiHandler = new Covid19IndiaApiHandler(restTemplate, properties, new Gson());
        covidIndia19Api = bindTo(restTemplate).build();
    }

    @Test
    public void getCovidApiResponseForDateSuccessTest(){
        final LocalDate now = LocalDate.now();
        try {
            URI constructedUri = constructUrlForCovidindia19Api(now,properties.getDatewiseSummaryUrl());
            covidIndia19Api.expect(requestTo(constructedUri))
                    .andExpect(method(GET))
                    .andRespond(withSuccess(getSampleResponseForCovidIndia19DateWiseApi(), APPLICATION_JSON));
            Optional<DatewiseIndiaCovidApiResponse> response = covid19IndiaApiHandler.getCovidApiResponseForDate(now);
            Assert.isTrue(response.isPresent());
            Assert.notEmpty(response.get().getStateMap());
        } catch (URISyntaxException | IOException e) {
            Assertions.fail("getCovidApiResponseForDateSuccessTest failed, message: " + e.getMessage());
        }
    }

    @Test
    public void getCovidApiResponseForDateOnErrorResponseFromAPITest(){
        final LocalDate now = LocalDate.now();
        try {
            URI constructedUri = constructUrlForCovidindia19Api(now,properties.getDatewiseSummaryUrl());
            covidIndia19Api.expect(requestTo(constructedUri))
                    .andExpect(method(GET))
                    .andRespond(withStatus(INTERNAL_SERVER_ERROR));
            Optional<DatewiseIndiaCovidApiResponse> response = covid19IndiaApiHandler.getCovidApiResponseForDate(now);
            Assert.isTrue(!response.isPresent());
        } catch (URISyntaxException  e) {
            Assertions.fail("getCovidApiResponseForDateOnErrorResponseFromAPITest failed, message: " + e.getMessage());
        }
    }
}
