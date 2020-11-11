package com.pritamprasad.covid_data_provider.service;

import com.pritamprasad.covid_data_provider.exception.EntityNotFoundException;
import com.pritamprasad.covid_data_provider.models.BaseResponse.Counts;
import com.pritamprasad.covid_data_provider.models.DistrictResponse;
import com.pritamprasad.covid_data_provider.models.LocationEntity;
import com.pritamprasad.covid_data_provider.models.MetaDataEntity;
import com.pritamprasad.covid_data_provider.repository.EntityRepository;
import com.pritamprasad.covid_data_provider.repository.MetadataRepository;
import com.pritamprasad.covid_data_provider.util.ModelMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pritamprasad.covid_data_provider.util.Messages.*;
import static com.pritamprasad.covid_data_provider.util.ModelMappers.getCountFromMeta;
import static com.pritamprasad.covid_data_provider.util.ModelMappers.getDistrictResponseFromLocationEntity;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service
public class DistrictHandlerService {

    private EntityRepository entityRepository;

    private MetadataRepository metadataRepository;

    @Autowired
    public DistrictHandlerService(final EntityRepository entityRepository, final MetadataRepository metadataRepository) {
        this.entityRepository = entityRepository;
        this.metadataRepository = metadataRepository;
    }

    public List<DistrictResponse> getAllDistrictByStateId(final long stateId) {
        List<LocationEntity> entities = entityRepository.findAllLocationEntityByParentId(stateId);
        if (entities.isEmpty()) {
            throw new EntityNotFoundException(format(NO_DISTRICT_FOR_STATE_FOUND_MESSAGE, stateId));
        }
        return entities.stream()
                .map(ModelMappers::getDistrictResponseFromLocationEntity)
                .collect(toList());
    }

    public DistrictResponse getDistrictsByIdOnDate(final long id, final LocalDate date) {
        final Optional<LocationEntity> district = entityRepository.findById(id);
        if (district.isPresent()) {
            final DistrictResponse response = getDistrictResponseFromLocationEntity(district.get());
            List<Counts> countsList = new ArrayList<>();
            Optional<MetaDataEntity> latestMetaByEntityId = metadataRepository.findMetaByEntityIdOnDate(district.get().getId(), date);
            latestMetaByEntityId.ifPresent(metaDataEntity -> countsList.add(getCountFromMeta(metaDataEntity)));
            response.setCounts(countsList);
            return response;
        } else {
            throw new EntityNotFoundException(format(NO_DISTRICT_FOUND_MESSAGE, id));
        }
    }

    public DistrictResponse getDistrictDataInBetweenDates(final long districtId, final LocalDate startDate, final LocalDate endDate) {
        Optional<LocationEntity> district = entityRepository.findById(districtId);
        if (district.isPresent()) {
            DistrictResponse response = getDistrictResponseFromLocationEntity(district.get());
            List<MetaDataEntity> allDistricts = metadataRepository.findAllByIdBetweenDates(districtId, startDate, endDate);
            List<Counts> counts = allDistricts.stream().map(ModelMappers::getCountFromMeta).collect(toList());
            response.setCounts(counts);
            return response;
        } else {
            throw new EntityNotFoundException(format(NO_DISTRICT_FOUND_MESSAGE, districtId));
        }
    }
}
