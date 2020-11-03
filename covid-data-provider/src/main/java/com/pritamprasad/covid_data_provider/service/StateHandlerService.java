package com.pritamprasad.covid_data_provider.service;

import com.pritamprasad.covid_data_provider.exception.EntityNotFoundException;
import com.pritamprasad.covid_data_provider.models.BaseResponse.Counts;
import com.pritamprasad.covid_data_provider.models.StateResponse;
import com.pritamprasad.covid_data_provider.repository.EntityRepository;
import com.pritamprasad.covid_data_provider.repository.LocationEntity;
import com.pritamprasad.covid_data_provider.repository.MetaDataEntity;
import com.pritamprasad.covid_data_provider.repository.MetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StateHandlerService {

    @Autowired
    private EntityRepository entityRepository;

    @Autowired
    private MetadataRepository metadataRepository;


    public List<StateResponse> getAllStates() {
        List<LocationEntity> entities =
                entityRepository.findAll().stream().filter(l -> l.getStateId() == 0).collect(Collectors.toList());
        return entities.stream().map(this::getStateResponseFromLocationEntity).collect(Collectors.toList());
    }

    private StateResponse getStateResponseFromLocationEntity(final LocationEntity state) {
        final StateResponse response = new StateResponse();
        response.setName(state.getName());
        response.setStateCode(state.getCode());
        List<MetaDataEntity> allMetaByStateId = metadataRepository.findAllMetaByEntityId(state.getId());
        response.setCounts(allMetaByStateId.stream().map(this::getCountFromMeta).collect(Collectors.toList()));
        return response;
    }

    private Counts getCountFromMeta(final MetaDataEntity m) {
       final Counts counts = new Counts();
        counts.setConfirmed(m.getConfirmed());
        counts.setDeceased(m.getDeceased());
        counts.setRecovered(m.getRecovered());
        counts.setTested(m.getTested());
        counts.setDate(m.getCreatedDate());
        return counts;
    }

    public StateResponse getStateByCode(String code) {
        Optional<LocationEntity> entity = entityRepository.findByCode(code);
        if(entity.isPresent()){
            return getStateResponseFromLocationEntity(entity.get());
        } else{
            throw new EntityNotFoundException(String.format("Entity code: %s", code));
        }
    }
}
