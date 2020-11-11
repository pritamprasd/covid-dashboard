package com.pritamprasad.covid_data_provider.service;

import com.google.gson.Gson;
import com.pritamprasad.covid_data_provider.models.DatewiseIndiaCovidApiResponse;
import com.pritamprasad.covid_data_provider.models.LocationEntity;
import com.pritamprasad.covid_data_provider.repository.EntityRepository;
import com.pritamprasad.covid_data_provider.repository.MetadataRepository;
import com.pritamprasad.covid_data_provider.utils.DataProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DatabaseUpdaterTest {

    private DatabaseUpdater databaseUpdater;

    private EntityRepository entityRepository;

    private MetadataRepository metadataRepository;

    @BeforeEach
    void setUp() {
        entityRepository = mock(EntityRepository.class);
        metadataRepository = mock(MetadataRepository.class);
        databaseUpdater = new DatabaseUpdater(entityRepository, metadataRepository);
    }

    @Test
    public void updateDatabaseSuccessTest(){
        Gson gson = new Gson();
        when(entityRepository.save(any(LocationEntity.class))).thenReturn(mock(LocationEntity.class));
        try {
            String staticResponseFromCovidApi = DataProvider.getSampleResponseForCovidIndia19DateWiseApi();
            DatewiseIndiaCovidApiResponse apiResponse = new DatewiseIndiaCovidApiResponse();
            final Map<String, Object> stateMap = gson.fromJson(staticResponseFromCovidApi, Map.class);
            stateMap.forEach((key, value) ->
                    apiResponse.getStateMap().put(key, gson.fromJson(gson.toJson(value), DatewiseIndiaCovidApiResponse.State.class)));
            databaseUpdater.updateDatabase(apiResponse);
        } catch (IOException e) {
            Assertions.fail("updateDatabaseSuccessTest failed, messsage: "+ e.getMessage());
        }
    }

    @Test
    public void updateDatabaseWithExistingLocationSuccessTest(){
        Gson gson = new Gson();
        when(entityRepository.save(any(LocationEntity.class))).thenReturn(mock(LocationEntity.class));
        when(entityRepository.findByNameAndParentId(any(),any())).thenReturn(Optional.of(mock(LocationEntity.class)));
        try {
            String staticResponseFromCovidApi = DataProvider.getSampleResponseForCovidIndia19DateWiseApi();
            DatewiseIndiaCovidApiResponse apiResponse = new DatewiseIndiaCovidApiResponse();
            final Map<String, Object> stateMap = gson.fromJson(staticResponseFromCovidApi, Map.class);
            stateMap.forEach((key, value) ->
                    apiResponse.getStateMap().put(key, gson.fromJson(gson.toJson(value), DatewiseIndiaCovidApiResponse.State.class)));
            databaseUpdater.updateDatabase(apiResponse);
        } catch (IOException e) {
            Assertions.fail("updateDatabaseWithExistingLocationSuccessTest failed, messsage: "+ e.getMessage());
        }
    }


}
