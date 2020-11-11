package com.pritamprasad.covid_data_provider.service;

import com.pritamprasad.covid_data_provider.repository.EntityRepository;
import com.pritamprasad.covid_data_provider.repository.MetadataRepository;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

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
}
