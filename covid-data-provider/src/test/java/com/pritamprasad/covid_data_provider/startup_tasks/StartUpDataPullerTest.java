package com.pritamprasad.covid_data_provider.startup_tasks;

import com.pritamprasad.covid_data_provider.client.CovidApi;
import com.pritamprasad.covid_data_provider.models.DatewiseIndiaCovidApiResponse;
import com.pritamprasad.covid_data_provider.repository.MetadataRepository;
import com.pritamprasad.covid_data_provider.service.DatabaseUpdater;
import com.pritamprasad.covid_data_provider.util.UserDefinedProperties;
import com.pritamprasad.covid_data_provider.utils.DataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.ApplicationArguments;

import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StartUpDataPullerTest {

    private MetadataRepository metadataRepository;
    private CovidApi covidApi;
    private UserDefinedProperties properties;
    private DatabaseUpdater databaseUpdater;

    private StartUpDataPuller startUpDataPuller;

    @BeforeEach
    public void setup(){
        metadataRepository = mock(MetadataRepository.class);
        covidApi = mock(CovidApi.class);
        databaseUpdater = mock(DatabaseUpdater.class);
        properties = DataProvider.getUserDefinedProperties();
        startUpDataPuller = new StartUpDataPuller(metadataRepository, covidApi, properties, databaseUpdater);
    }


    @Test
    public void poolStartupDataSuccessTest(){
        ApplicationArguments arguments = mock(ApplicationArguments.class);
        when(metadataRepository.finalAllAvailableDatesSortByCreatedDate()).thenReturn(emptyList());
        when(covidApi.getCovidApiResponseForDate(any())).thenReturn(Optional.of(mock(DatewiseIndiaCovidApiResponse.class)));
        try {
            startUpDataPuller.run(arguments);
        } catch (Exception e) {
            fail("poolStartupDataSuccessTest failed..");
        }
    }




}
