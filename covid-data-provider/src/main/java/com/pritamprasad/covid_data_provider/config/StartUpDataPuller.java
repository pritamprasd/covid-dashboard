package com.pritamprasad.covid_data_provider.config;

import com.pritamprasad.covid_data_provider.client.Covid19IndiaApiHandler;
import com.pritamprasad.covid_data_provider.client.DatewiseIndiaCovidApiResponse;
import com.pritamprasad.covid_data_provider.models.EntityType;
import com.pritamprasad.covid_data_provider.repository.LocationEntity;
import com.pritamprasad.covid_data_provider.repository.MetaDataEntity;
import com.pritamprasad.covid_data_provider.repository.EntityRepository;
import com.pritamprasad.covid_data_provider.repository.MetadataRepository;
import com.pritamprasad.covid_data_provider.util.UserDefinedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Component
public class StartUpDataPuller implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(StartUpDataPuller.class);

    private EntityRepository entityRepository;

    private MetadataRepository metadataRepository;

    private Covid19IndiaApiHandler covid19IndiaApiHandler;

    private UserDefinedProperties properties;

    @Autowired
    public StartUpDataPuller(EntityRepository entityRepository, MetadataRepository metadataRepository,
                             Covid19IndiaApiHandler covid19IndiaApiHandler, UserDefinedProperties properties) {
        this.entityRepository = entityRepository;
        this.metadataRepository = metadataRepository;
        this.covid19IndiaApiHandler = covid19IndiaApiHandler;
        this.properties = properties;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LocalDate today = LocalDate.now();
        for(int i =0; i<properties.getPastDataInNumberOfDays() ; i++){
            final LocalDate now = today.minusDays(properties.getTimeTravelMinusDays() + i);
            // Add country Data handling here, for now only India, will need a new api
            DatewiseIndiaCovidApiResponse summary = covid19IndiaApiHandler.getSummaryForDate(now);
            updateDatabase(summary);
        }
    }

    private void updateDatabase(DatewiseIndiaCovidApiResponse summary) {
        /**
         * TODO: use timestamp in long to save metadata
         *
         */
//        Optional<LocationEntity> savedCountry = entityRepository.findByName("state.getKey()");
        for(Map.Entry<String, DatewiseIndiaCovidApiResponse.State> state : summary.getStateMap().entrySet()){
            int currentStateId;
            Optional<LocationEntity> savedState = entityRepository.findByName(state.getKey());
            if(savedState.isPresent()){
                currentStateId = savedState.get().getId();
            } else {
                logger.debug("Creating new State Entity..");
                LocationEntity stateEntity = new LocationEntity();
                stateEntity.setName(state.getKey());
                stateEntity.setCode(state.getKey());
                stateEntity.setType(EntityType.STATE);
                stateEntity.setPopulation(state.getValue().getMeta().getPopulation());
                stateEntity.setCreatedDate(summary.getDateStamp());
                currentStateId = entityRepository.save(stateEntity).getId();
            }

            MetaDataEntity stateMetadataEntity = new MetaDataEntity();
            stateMetadataEntity.setConfirmed(state.getValue().getTotal().getConfirmed());
            stateMetadataEntity.setDeceased(state.getValue().getTotal().getDeceased());
            stateMetadataEntity.setRecovered(state.getValue().getTotal().getRecovered());
            stateMetadataEntity.setTested(state.getValue().getTotal().getTested());
            stateMetadataEntity.setEntityId(currentStateId);
            stateMetadataEntity.setCreatedDate(summary.getDateStamp());
            metadataRepository.save(stateMetadataEntity);

            for (Map.Entry<String, DatewiseIndiaCovidApiResponse.Districts> district :
                    Optional.ofNullable(state.getValue().getDistricts()).orElse(Collections.emptyMap()).entrySet()){
                int currentDistrictId;
                Optional<LocationEntity> savedDistrict = entityRepository.findByName(district.getKey());
                if(savedDistrict.isPresent()){
                    currentDistrictId = savedDistrict.get().getId();
                } else {
                    logger.debug("Creating new District Entity..");
                    LocationEntity districtEntity = new LocationEntity();
                    districtEntity.setName(district.getKey());
                    districtEntity.setCode(district.getKey());
                    districtEntity.setPopulation(Optional.ofNullable(district.getValue().getMeta()).orElse(new DatewiseIndiaCovidApiResponse.Meta()).getPopulation());
                    districtEntity.setParentId(currentStateId);
                    districtEntity.setType(EntityType.DISTRICT);
                    districtEntity.setCreatedDate(summary.getDateStamp());
                    currentDistrictId = entityRepository.save(districtEntity).getId();
                }

                MetaDataEntity districtMetaDataEntity = new MetaDataEntity();
                districtMetaDataEntity.setConfirmed(state.getValue().getTotal().getConfirmed());
                districtMetaDataEntity.setDeceased(state.getValue().getTotal().getDeceased());
                districtMetaDataEntity.setRecovered(state.getValue().getTotal().getRecovered());
                districtMetaDataEntity.setTested(state.getValue().getTotal().getTested());
                districtMetaDataEntity.setEntityId(currentDistrictId);
                districtMetaDataEntity.setCreatedDate(summary.getDateStamp());
                metadataRepository.save(districtMetaDataEntity);
            }
        }
    }
}
