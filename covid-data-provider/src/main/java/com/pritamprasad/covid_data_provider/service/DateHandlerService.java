package com.pritamprasad.covid_data_provider.service;

import com.pritamprasad.covid_data_provider.exception.InternalErrorException;
import com.pritamprasad.covid_data_provider.models.DateRangeResponse;
import com.pritamprasad.covid_data_provider.repository.MetadataRepository;
import com.pritamprasad.covid_data_provider.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DateHandlerService {

    private MetadataRepository metadataRepository;

    @Autowired
    public DateHandlerService(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    public DateRangeResponse getAvailableDateRange() {
        final List<LocalDate> localDates = metadataRepository.finalAllAvailableDatesSortByCreatedDate();
        if(!localDates.isEmpty()){
            return new DateRangeResponse(localDates.get(0), localDates.get(localDates.size() - 1));
        } else{
            throw new InternalErrorException(Messages.DATE_RANGE_ERROR_MSG);
        }
    }
}
