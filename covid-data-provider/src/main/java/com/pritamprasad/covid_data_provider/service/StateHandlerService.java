package com.pritamprasad.covid_data_provider.service;

import com.pritamprasad.covid_data_provider.exception.EntityNotFoundException;
import com.pritamprasad.covid_data_provider.models.BaseResponse.Counts;
import com.pritamprasad.covid_data_provider.models.EntityType;
import com.pritamprasad.covid_data_provider.models.StateResponse;
import com.pritamprasad.covid_data_provider.repository.EntityRepository;
import com.pritamprasad.covid_data_provider.repository.LocationEntity;
import com.pritamprasad.covid_data_provider.repository.MetaDataEntity;
import com.pritamprasad.covid_data_provider.repository.MetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pritamprasad.covid_data_provider.util.Helper.getCountFromMeta;

@Service
public class StateHandlerService {

    private EntityRepository entityRepository;

    private MetadataRepository metadataRepository;

    @Autowired
    public StateHandlerService(EntityRepository entityRepository, MetadataRepository metadataRepository) {
        this.entityRepository = entityRepository;
        this.metadataRepository = metadataRepository;
    }


    public List<StateResponse> getAllStates() {
        List<LocationEntity> entities =
                entityRepository.findAll().stream().filter(l -> l.getType() == EntityType.STATE).collect(Collectors.toList());
        return entities.stream().map(this::getStateResponseFromLocationEntity).collect(Collectors.toList());
    }

    public StateResponse getStateById(Long id, LocalDate date) {
        Optional<LocationEntity> entity = entityRepository.findById(id);
        if(entity.isPresent()){
            return getStateResponseWithLatestMetadataFromLocationEntity(entity.get(),date);
        } else{
            throw new EntityNotFoundException(String.format("Entity id: %s", id));
        }
    }

    private StateResponse getStateResponseFromLocationEntity(final LocationEntity state) {
        final StateResponse response = new StateResponse();
        response.setName(state.getName());
        response.setStateCode(state.getCode());
        response.setId(state.getId());
        return response;
    }

    private StateResponse getStateResponseWithLatestMetadataFromLocationEntity(final LocationEntity state, LocalDate date) {
        final StateResponse response = new StateResponse();
        response.setName(state.getName());
        response.setStateCode(state.getCode());
        response.setId(state.getId());
        List<Counts> countsList = new ArrayList<>();
        Optional<MetaDataEntity> latestMetaByEntityId =
                metadataRepository.findLatestMetaByEntityId(state.getId(), date);
        latestMetaByEntityId.ifPresent(metaDataEntity -> countsList.add(getCountFromMeta(metaDataEntity)));
        response.setCounts(countsList);
        return response;
    }

}
