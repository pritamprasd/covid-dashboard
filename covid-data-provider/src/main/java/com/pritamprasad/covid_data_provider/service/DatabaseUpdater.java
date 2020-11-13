package com.pritamprasad.covid_data_provider.service;

import com.pritamprasad.covid_data_provider.models.DatewiseIndiaCovidApiResponse;
import com.pritamprasad.covid_data_provider.models.DatewiseIndiaCovidApiResponse.District;
import com.pritamprasad.covid_data_provider.models.DatewiseIndiaCovidApiResponse.State;
import com.pritamprasad.covid_data_provider.models.LocationEntity;
import com.pritamprasad.covid_data_provider.models.MetaData;
import com.pritamprasad.covid_data_provider.models.MetaDataEntity;
import com.pritamprasad.covid_data_provider.repository.EntityRepository;
import com.pritamprasad.covid_data_provider.repository.MetadataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map.Entry;
import java.util.Optional;

import static com.pritamprasad.covid_data_provider.models.EntityType.DISTRICT;
import static com.pritamprasad.covid_data_provider.models.EntityType.STATE;
import static com.pritamprasad.covid_data_provider.util.Messages.CREATING_NEW_DISTRICT_ENTITY;
import static com.pritamprasad.covid_data_provider.util.Messages.CREATING_NEW_STATE_ENTITY;
import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;

@Service
public class DatabaseUpdater {

    private final Logger logger = LoggerFactory.getLogger(DatabaseUpdater.class);

    private EntityRepository entityRepository;

    private MetadataRepository metadataRepository;

    @Autowired
    public DatabaseUpdater(EntityRepository entityRepository, MetadataRepository metadataRepository) {
        this.entityRepository = entityRepository;
        this.metadataRepository = metadataRepository;
    }

    public void updateDatabase(DatewiseIndiaCovidApiResponse summary) {
        final LocalDate currentDate = summary.getDateStamp();
        for (Entry<String, State> state : summary.getStateMap().entrySet()) {
            long currentStateId;
            currentStateId = createStateIfNotAvailable(currentDate,0, state);
            if (state.getValue().getTotal() != null) {
                updateMetaData(currentDate, currentStateId, state.getValue().getTotal());
            }
            for (Entry<String, District> district : ofNullable(state.getValue().getDistricts()).orElse(emptyMap()).entrySet()) {
                long currentDistrictId;
                currentDistrictId = createDistrictIfNotAvailable(currentDate, currentStateId, district);
                if (district.getValue().getTotal() != null) {
                    updateMetaData(currentDate, currentDistrictId, district.getValue().getTotal());
                }
            }
        }
    }

    private void updateMetaData(LocalDate currentDate, long currentStateId, MetaData total) {
        MetaDataEntity stateMetadataEntity = MetaDataEntity.builder()
                .confirmed(total.getConfirmed())
                .deceased(total.getDeceased())
                .recovered(total.getRecovered())
                .tested(total.getTested())
                .entityId(currentStateId)
                .createdDate(currentDate).build();
        metadataRepository.save(stateMetadataEntity);
    }

    private long createDistrictIfNotAvailable(final LocalDate currentDate, final long currentStateId, final Entry<String, District> district) {
        long currentDistrictId;
        Optional<LocationEntity> savedDistrict = entityRepository.findByNameAndParentId(district.getKey(), currentStateId);
        if (savedDistrict.isPresent()) {
            currentDistrictId = savedDistrict.get().getId();
        } else {
            logger.debug(CREATING_NEW_DISTRICT_ENTITY, district.getKey());
            final LocationEntity districtEntity = LocationEntity.builder()
                    .name(district.getKey())
                    .code(district.getKey())
                    .population(ofNullable(district.getValue().getMeta()).orElse(new DatewiseIndiaCovidApiResponse.Meta()).getPopulation())
                    .parentId(currentStateId)
                    .type(DISTRICT)
                    .createdDate(currentDate).build();
            districtEntity.setCreatedDate(currentDate);
            currentDistrictId = entityRepository.save(districtEntity).getId();
        }
        return currentDistrictId;
    }

    private long createStateIfNotAvailable(final LocalDate currentDate, final long currentCountryId, final Entry<String, State> state) {
        long currentStateId;
        Optional<LocationEntity> savedState = entityRepository.findByNameAndParentId(state.getKey(), currentCountryId);
        if (savedState.isPresent()) {
            currentStateId = savedState.get().getId();
        } else {
            logger.debug(CREATING_NEW_STATE_ENTITY, state.getKey());
            LocationEntity stateEntity = LocationEntity.builder()
                    .name(state.getKey())
                    .code(state.getKey())
                    .population(state.getValue().getMeta().getPopulation())
                    .parentId(currentCountryId)
                    .type(STATE)
                    .createdDate(currentDate).build();
            currentStateId = entityRepository.save(stateEntity).getId();
        }
        return currentStateId;
    }

}
