package com.pritamprasad.covid_data_provider.service;

import com.pritamprasad.covid_data_provider.exception.EntityNotFoundException;
import com.pritamprasad.covid_data_provider.models.LocationEntity;
import com.pritamprasad.covid_data_provider.models.MetaDataEntity;
import com.pritamprasad.covid_data_provider.models.StateResponse;
import com.pritamprasad.covid_data_provider.repository.EntityRepository;
import com.pritamprasad.covid_data_provider.repository.MetadataRepository;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StateHandlerServiceTest {

    private StateHandlerService stateHandlerService;

    private EntityRepository entityRepository;

    private MetadataRepository metadataRepository;

    @BeforeEach
    void setUp() {
        entityRepository = mock(EntityRepository.class);
        metadataRepository = mock(MetadataRepository.class);
        stateHandlerService = new StateHandlerService(entityRepository, metadataRepository);
    }

    @Test
    public void getAllStatesByCountryIdSuccessTest() {
        List<LocationEntity> testEntities = new ArrayList<>();
        String test_entityName = "Test_Entity";
        long countryId = 0L;
        long entityId = 1001L;
        testEntities.add(LocationEntity.builder()
                .name(test_entityName)
                .parentId(countryId)
                .id(entityId)
                .build()
        );
        when(entityRepository.findAllLocationEntityByParentId(countryId)).thenReturn(testEntities);
        List<StateResponse> allStatesByCountryId = stateHandlerService.getAllStatesByCountryId(countryId);
        Assert.isTrue(!allStatesByCountryId.isEmpty());
        Assert.isTrue(allStatesByCountryId.get(0).getCountryId().equals(countryId));
        Assert.isTrue(allStatesByCountryId.get(0).getId() == entityId);
        Assert.isTrue(allStatesByCountryId.get(0).getName().equals(test_entityName));
    }

    @Test
    public void getAllStatesByCountryIdEntityNotFoundExceptionTest() {
        when(entityRepository.findAllLocationEntityByParentId(100L)).thenReturn(emptyList());
        try {
            List<StateResponse> allDistrictByStateId = stateHandlerService.getAllStatesByCountryId(100L);
            Assertions.fail("getAllStatesByCountryIdEntityNotFoundExceptionTest failed");
        } catch (EntityNotFoundException ex) {
            //Test passes
        }
    }

    @Test
    public void getStateByIdOnDateSuccessTest() {
        String test_entityName = "Test_Entity";
        long countryId = 100L;
        long entityId = 1001L;
        LocalDate testDate = LocalDate.now();
        LocationEntity entity = LocationEntity.builder().name(test_entityName).parentId(countryId).id(entityId).build();
        MetaDataEntity metaDataEntity = MetaDataEntity.builder().confirmed(0L).deceased(0L).recovered(0L).tested(0L)
                .entityId(entityId).createdDate(testDate).build();
        when(entityRepository.findById(entityId)).thenReturn(of(entity));
        when(metadataRepository.findMetaByEntityIdOnDate(entityId, testDate)).thenReturn(Optional.of(metaDataEntity));

        StateResponse districtsByIdOnDate = stateHandlerService.getStateByIdOnDate(entityId, testDate);
        Assert.isTrue(districtsByIdOnDate.getName().equals(test_entityName));
        Assert.isTrue(districtsByIdOnDate.getCounts().get(0).getConfirmed() == 0L);
    }

    @Test
    public void getStateByIdOnDateEntityNotFoundExceptionTest() {
        long entityId = 1001L;
        LocalDate testDate = LocalDate.now();
        when(entityRepository.findById(entityId)).thenReturn(Optional.empty());
        try {
            stateHandlerService.getStateByIdOnDate(entityId, testDate);
            Assertions.fail("getStateByIdOnDateEntityNotFoundExceptionTest failed.");
        } catch (EntityNotFoundException ex) {
            //Test passes
        }
    }

    @Test
    public void getStateDataInBetweenSuccessTest() {
        String test_entityName = "Test_Entity";
        long countryId = 100L;
        long entityId = 1001L;
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);

        LocationEntity entity = LocationEntity.builder().name(test_entityName).parentId(countryId).id(entityId).build();
        List<MetaDataEntity> metaDataEntities = new ArrayList<>();
        metaDataEntities.add(MetaDataEntity.builder().confirmed(0L).deceased(0L).recovered(0L).tested(0L)
                .entityId(entityId).createdDate(start).build());
        metaDataEntities.add(MetaDataEntity.builder().confirmed(10L).deceased(10L).recovered(10L).tested(10L)
                .entityId(entityId).createdDate(end).build());

        when(entityRepository.findById(entityId)).thenReturn(of(entity));
        when(metadataRepository.findAllByIdBetweenDates(entityId, start, end)).thenReturn(metaDataEntities);

        StateResponse districtsByIdOnDate = stateHandlerService.getStateDataInBetween(entityId, start,end);

        Assert.isTrue(districtsByIdOnDate.getName().equals(test_entityName));
        Assert.isTrue(districtsByIdOnDate.getCounts().get(0).getConfirmed() == 0L);
        Assert.isTrue(districtsByIdOnDate.getCounts().get(1).getConfirmed() == 10L);
    }

    @Test
    public void getStateDataInBetweenEntityNotFoundExceptionTest() {
        long entityId = 1001L;
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);
        when(entityRepository.findById(entityId)).thenReturn(Optional.empty());
        try{
            StateResponse districtsByIdOnDate = stateHandlerService.getStateDataInBetween(entityId, start,end);
            Assertions.fail("getStateDataInBetweenEntityNotFoundExceptionTest failed");
        } catch (EntityNotFoundException ex){
            //Test Passes
        }
    }

}
