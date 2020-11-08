package com.pritamprasad.covid_data_provider.service;

import com.pritamprasad.covid_data_provider.exception.InternalErrorException;
import com.pritamprasad.covid_data_provider.models.DateRangeResponse;
import com.pritamprasad.covid_data_provider.repository.MetaDataEntity;
import com.pritamprasad.covid_data_provider.repository.MetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class DateHandlerService {

    private MetadataRepository metadataRepository;

    public DateHandlerService(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    public DateRangeResponse getAvailableDateRange() {
        Page<MetaDataEntity> latestOverallMetaData = metadataRepository.findLatestOverallMetaData(PageRequest.of(0, 1));
        Page<MetaDataEntity> oldestOverallMetaData = metadataRepository.findOldestOverallMetaData(PageRequest.of(0, 1));
        if (latestOverallMetaData.getContent().isEmpty() || oldestOverallMetaData.getContent().isEmpty()) {
            throw new InternalErrorException("could not able to get date range");
        } else {
            return new DateRangeResponse(latestOverallMetaData.getContent().get(0).getCreatedDate(),
                    oldestOverallMetaData.getContent().get(0).getCreatedDate());
        }
    }
}
