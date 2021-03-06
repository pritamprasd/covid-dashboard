package com.pritamprasad.covid_data_provider.service;

import com.pritamprasad.covid_data_provider.exception.EntityNotFoundException;
import com.pritamprasad.covid_data_provider.models.DistrictResponse;
import com.pritamprasad.covid_data_provider.models.LocationEntity;
import com.pritamprasad.covid_data_provider.models.MetaDataEntity;
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

public class DistrictHandlerServiceTest {

    private DistrictHandlerService districtHandlerService;

    private EntityRepository entityRepository;

    private MetadataRepository metadataRepository;

    @BeforeEach
    void setUp() {
        entityRepository = mock(EntityRepository.class);
        metadataRepository = mock(MetadataRepository.class);
        districtHandlerService = new DistrictHandlerService(entityRepository, metadataRepository);
    }

    @Test
    public void getAllDistrictByStateIdSuccessTest() {
        List<LocationEntity> testEntities = new ArrayList<>();
        String test_entityName = "Test_Entity";
        long stateId = 100L;
        long entityId = 1001L;
        testEntities.add(LocationEntity.builder()
                .name(test_entityName)
                .parentId(stateId)
                .id(entityId)
                .build()
        );
        when(entityRepository.findAllLocationEntityByParentId(stateId)).thenReturn(testEntities);
        List<DistrictResponse> allDistrictByStateId = districtHandlerService.getAllDistrictByStateId(stateId);
        Assert.isTrue(!allDistrictByStateId.isEmpty());
        Assert.isTrue(allDistrictByStateId.get(0).getStateId().equals(stateId));
        Assert.isTrue(allDistrictByStateId.get(0).getId() == entityId);
        Assert.isTrue(allDistrictByStateId.get(0).getName().equals(test_entityName));
    }

    @Test
    public void getAllDistrictByStateIdEntityNotFoundExceptionTest() {
        when(entityRepository.findAllLocationEntityByParentId(100L)).thenReturn(emptyList());
        try {
            List<DistrictResponse> allDistrictByStateId = districtHandlerService.getAllDistrictByStateId(100L);
            Assertions.fail("getAllDistrictByStateIdEntityNotFoundExceptionTest failed");
        } catch (EntityNotFoundException ex) {
            //Test passes
        }
    }

    @Test
    public void getDistrictsByIdOnDateSuccessTest() {
        String test_entityName = "Test_Entity";
        long stateId = 100L;
        long entityId = 1001L;
        LocalDate testDate = LocalDate.now();
        ;
        LocationEntity entity = LocationEntity.builder().name(test_entityName).parentId(stateId).id(entityId).build();
        MetaDataEntity metaDataEntity = MetaDataEntity.builder().confirmed(0L).deceased(0L).recovered(0L).tested(0L)
                .entityId(entityId).createdDate(testDate).build();
        when(entityRepository.findById(entityId)).thenReturn(of(entity));
        when(metadataRepository.findMetaByEntityIdOnDate(entityId, testDate)).thenReturn(Optional.of(metaDataEntity));

        DistrictResponse districtsByIdOnDate = districtHandlerService.getDistrictsByIdOnDate(entityId, testDate);
        Assert.isTrue(districtsByIdOnDate.getName().equals(test_entityName));
        Assert.isTrue(districtsByIdOnDate.getCounts().get(0).getConfirmed() == 0L);
    }

    @Test
    public void getDistrictsByIdOnDateEntityNotFoundExceptionTest() {
        long entityId = 1001L;
        LocalDate testDate = LocalDate.now();
        ;
        when(entityRepository.findById(entityId)).thenReturn(Optional.empty());
        try {
            districtHandlerService.getDistrictsByIdOnDate(entityId, testDate);
            Assertions.fail("getDistrictsByIdOnDateEntityNotFoundExceptionTest failed.");
        } catch (EntityNotFoundException ex) {
            //Test passes
        }
    }

    @Test
    public void getDistrictDataInBetweenDatesSuccessTest() {
        String test_entityName = "Test_Entity";
        long stateId = 100L;
        long entityId = 1001L;
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);

        LocationEntity entity = LocationEntity.builder().name(test_entityName).parentId(stateId).id(entityId).build();
        List<MetaDataEntity> metaDataEntities = new ArrayList<>();
        metaDataEntities.add(MetaDataEntity.builder().confirmed(0L).deceased(0L).recovered(0L).tested(0L)
                .entityId(entityId).createdDate(start).build());
        metaDataEntities.add(MetaDataEntity.builder().confirmed(10L).deceased(10L).recovered(10L).tested(10L)
                .entityId(entityId).createdDate(end).build());

        when(entityRepository.findById(entityId)).thenReturn(of(entity));
        when(metadataRepository.findAllByIdBetweenDates(entityId, start, end)).thenReturn(metaDataEntities);

        DistrictResponse districtsByIdOnDate = districtHandlerService.getDistrictDataInBetweenDates(entityId, start,end);

        Assert.isTrue(districtsByIdOnDate.getName().equals(test_entityName));
        Assert.isTrue(districtsByIdOnDate.getCounts().get(0).getConfirmed() == 0L);
        Assert.isTrue(districtsByIdOnDate.getCounts().get(1).getConfirmed() == 10L);
    }

    @Test
    public void getDistrictDataInBetweenDatesEntityNotFoundExceptionTest() {
        long entityId = 1001L;
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);
        when(entityRepository.findById(entityId)).thenReturn(Optional.empty());
        try{
            DistrictResponse districtsByIdOnDate = districtHandlerService.getDistrictDataInBetweenDates(entityId, start,end);
            Assertions.fail("getDistrictDataInBetweenDatesEntityNotFoundExceptionTest failed");
        } catch (EntityNotFoundException ex){
            //Test Passes
        }
    }

}
