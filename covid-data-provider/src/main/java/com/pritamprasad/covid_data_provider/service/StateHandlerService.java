package com.pritamprasad.covid_data_provider.service;

import com.pritamprasad.covid_data_provider.exception.EntityNotFoundException;
import com.pritamprasad.covid_data_provider.models.BaseResponse.Counts;
import com.pritamprasad.covid_data_provider.models.LocationEntity;
import com.pritamprasad.covid_data_provider.models.MetaDataEntity;
import com.pritamprasad.covid_data_provider.models.StateResponse;
import com.pritamprasad.covid_data_provider.repository.EntityRepository;
import com.pritamprasad.covid_data_provider.repository.MetadataRepository;
import com.pritamprasad.covid_data_provider.util.ModelMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pritamprasad.covid_data_provider.util.Messages.NO_STATES_FOUND_FOR_COUNTRY_ID;
import static com.pritamprasad.covid_data_provider.util.Messages.STATE_NOT_FOUND_MSG;
import static com.pritamprasad.covid_data_provider.util.ModelMappers.getCountFromMeta;
import static com.pritamprasad.covid_data_provider.util.ModelMappers.getStateResponseFromLocationEntity;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service
public class StateHandlerService {

    private EntityRepository entityRepository;

    private MetadataRepository metadataRepository;

    @Autowired
    public StateHandlerService(EntityRepository entityRepository, MetadataRepository metadataRepository) {
        this.entityRepository = entityRepository;
        this.metadataRepository = metadataRepository;
    }

    public List<StateResponse> getAllStatesByCountryId(final long countryId) {
        List<LocationEntity> entities = entityRepository.findAllLocationEntityByParentId(countryId);
        if (entities.isEmpty()) {
            throw new EntityNotFoundException(format(NO_STATES_FOUND_FOR_COUNTRY_ID, countryId));
        }
        return entities.stream()
                .map(ModelMappers::getStateResponseFromLocationEntity)
                .collect(toList());
    }

    public StateResponse getStateByIdOnDate(Long id, LocalDate date) {
        Optional<LocationEntity> state = entityRepository.findById(id);
        if (state.isPresent()) {
            final StateResponse response = getStateResponseFromLocationEntity(state.get());
            List<Counts> countsList = new ArrayList<>();
            Optional<MetaDataEntity> latestMetaByEntityId = metadataRepository.findMetaByEntityIdOnDate(state.get().getId(), date);
            latestMetaByEntityId.ifPresent(metaDataEntity -> countsList.add(getCountFromMeta(metaDataEntity)));
            response.setCounts(countsList);
            return response;
        } else {
            throw new EntityNotFoundException(format(STATE_NOT_FOUND_MSG, id));
        }
    }

    public StateResponse getStateDataInBetween(long stateId, LocalDate startDate, LocalDate endDate) {
        Optional<LocationEntity> entity = entityRepository.findById(stateId);
        if (entity.isPresent()) {
            StateResponse response = getStateResponseFromLocationEntity(entity.get());
            List<MetaDataEntity> allStates = metadataRepository.findAllByIdBetweenDates(stateId, startDate, endDate);
            List<Counts> counts = allStates.stream().map(ModelMappers::getCountFromMeta).collect(Collectors.toList());
            response.setCounts(counts);
            return response;
        } else {
            throw new EntityNotFoundException(format(STATE_NOT_FOUND_MSG, stateId));
        }
    }
}
