package com.pritamprasad.covid_data_provider.service;

import com.pritamprasad.covid_data_provider.exception.EntityNotFoundException;
import com.pritamprasad.covid_data_provider.models.BaseResponse.Counts;
import com.pritamprasad.covid_data_provider.models.DistrictResponse;
import com.pritamprasad.covid_data_provider.repository.EntityRepository;
import com.pritamprasad.covid_data_provider.repository.LocationEntity;
import com.pritamprasad.covid_data_provider.repository.MetaDataEntity;
import com.pritamprasad.covid_data_provider.repository.MetadataRepository;
import com.pritamprasad.covid_data_provider.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pritamprasad.covid_data_provider.util.Helper.getCountFromMeta;

@Service
public class DistrictHandlerService {

    private EntityRepository entityRepository;

    private MetadataRepository metadataRepository;

    @Autowired
    public DistrictHandlerService(EntityRepository entityRepository, MetadataRepository metadataRepository) {
        this.entityRepository = entityRepository;
        this.metadataRepository = metadataRepository;
    }

    public List<DistrictResponse> getAllDistrictByState(long stateId) {
        List<LocationEntity> entities = entityRepository.findAllEntityByParentId(stateId);
        return entities.stream()
                .map(district -> getDistrictResponseFromLocationEntity(district, stateId))
                .collect(Collectors.toList());
    }

    public DistrictResponse getDistrictById(long id, LocalDate date) {
        Optional<LocationEntity> entity = entityRepository.findById(id);
        if (entity.isPresent()) {
            return getDistrictResponseWithLatestMetadataFromLocationEntity(entity.get(), date);
        } else {
            throw new EntityNotFoundException(String.format("Entity id: %s", id));
        }
    }

    private DistrictResponse getDistrictResponseFromLocationEntity(LocationEntity district, long stateId) {
        final DistrictResponse response = new DistrictResponse();
        response.setName(district.getName());
        response.setStateId(stateId);
        response.setId(district.getId());
        return response;
    }

    private DistrictResponse getDistrictResponseWithLatestMetadataFromLocationEntity(final LocationEntity district, LocalDate date) {
        final DistrictResponse response = new DistrictResponse();
        response.setName(district.getName());
        response.setStateId(district.getParentId());
        response.setId(district.getId());
        List<Counts> countsList = new ArrayList<>();
        Optional<MetaDataEntity> latestMetaByEntityId =
                metadataRepository.findMetaByEntityIdOnDate(district.getId(), date);
        latestMetaByEntityId.ifPresent(metaDataEntity -> countsList.add(getCountFromMeta(metaDataEntity)));
        response.setCounts(countsList);
        return response;
    }

    public DistrictResponse getStateDataInBetween(long stateId, LocalDate startDate, LocalDate endDate) {
        DistrictResponse response;
        Optional<LocationEntity> entity = entityRepository.findById(stateId);
        if (entity.isPresent()) {
            response = getDistrictResponseFromLocationEntity(entity.get());
            List<MetaDataEntity> allByIdBetweenDates = metadataRepository.findAllByIdBetweenDates(stateId, startDate, endDate);
            List<Counts> counts = allByIdBetweenDates.stream().map(meta -> getCountFromMeta(meta)).collect(Collectors.toList());
            response.setCounts(counts);
        } else {
            throw new EntityNotFoundException(String.format(Messages.STATE_NOT_FOUND_MSG, stateId));
        }
        return response;
    }

    private DistrictResponse getDistrictResponseFromLocationEntity(final LocationEntity state) {
        final DistrictResponse response = new DistrictResponse();
        response.setName(state.getName());
        response.setStateId(state.getParentId());
        response.setId(state.getId());
        return response;
    }
}
