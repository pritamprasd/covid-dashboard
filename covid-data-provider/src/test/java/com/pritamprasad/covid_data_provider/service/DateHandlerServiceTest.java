package com.pritamprasad.covid_data_provider.service;

import com.pritamprasad.covid_data_provider.exception.InternalErrorException;
import com.pritamprasad.covid_data_provider.models.DateRangeResponse;
import com.pritamprasad.covid_data_provider.repository.MetadataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static io.jsonwebtoken.lang.Assert.isTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DateHandlerServiceTest {

    private DateHandlerService dateHandlerService;

    private MetadataRepository metadataRepository;

    @BeforeEach
    void setUp() {
        metadataRepository = mock(MetadataRepository.class);
        dateHandlerService = new DateHandlerService(metadataRepository);
    }

    @Test
    public void getAvailableDateRangeSuccessTest(){
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.minusDays(10);
        List<LocalDate> allDates = new ArrayList<>();
        LocalDate currentDate = startDate;
        while(!currentDate.isEqual(endDate)){
            allDates.add(LocalDate.from(currentDate));
            currentDate = currentDate.minusDays(1);
        }
        when(metadataRepository.finalAllAvailableDatesSortByCreatedDate()).thenReturn(allDates);
        DateRangeResponse availableDateRange = dateHandlerService.getAvailableDateRange();
        isTrue(availableDateRange.getMaxDate().isEqual(startDate));
        isTrue(availableDateRange.getMinDate().isEqual(endDate.plusDays(1)));
    }

    @Test
    public void getAvailableDateRangeInternalErrorExceptionTest(){
        try {
            List<LocalDate> allDates = new ArrayList<>();
            when(metadataRepository.finalAllAvailableDatesSortByCreatedDate()).thenReturn(allDates);
            DateRangeResponse availableDateRange = dateHandlerService.getAvailableDateRange();
            fail("getAvailableDateRangeInternalErrorExceptionTest failed");
        } catch (InternalErrorException e){
            //Test passes
        }
    }
}
