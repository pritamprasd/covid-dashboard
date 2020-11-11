package com.pritamprasad.covid_data_provider.startup_tasks;

import com.pritamprasad.covid_data_provider.client.CovidApi;
import com.pritamprasad.covid_data_provider.models.DatewiseIndiaCovidApiResponse;
import com.pritamprasad.covid_data_provider.repository.MetadataRepository;
import com.pritamprasad.covid_data_provider.service.DatabaseUpdater;
import com.pritamprasad.covid_data_provider.util.UserDefinedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class StartUpDataPuller implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(StartUpDataPuller.class);

    private MetadataRepository metadataRepository;

    private CovidApi covidApi;

    private UserDefinedProperties properties;

    private DatabaseUpdater databaseUpdater;

    @Autowired
    public StartUpDataPuller(MetadataRepository metadataRepository, CovidApi covidApi,
                             UserDefinedProperties properties, DatabaseUpdater databaseUpdater) {
        this.metadataRepository = metadataRepository;
        this.covidApi = covidApi;
        this.properties = properties;
        this.databaseUpdater = databaseUpdater;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final LocalDate today = LocalDate.now();
        final LocalDate startDate = today.minusDays(properties.getTimeTravelMinusDays());
        final LocalDate dayBeforeEndDate = startDate.minusDays(properties.getPastDataInNumberOfDays() + 1L);
        List<LocalDate> availableDatesInDb = metadataRepository.finalAllAvailableDatesSortByCreatedDate();
        LocalDate currentDate = startDate;
        while (!currentDate.isEqual(dayBeforeEndDate)) {
            if (!availableDatesInDb.contains(currentDate)) {
                logger.debug("Pulling data for date : {}", currentDate);
                Optional<DatewiseIndiaCovidApiResponse> summary = covidApi.getCovidApiResponseForDate(currentDate);
                summary.ifPresent(apiResponse -> databaseUpdater.updateDatabase(apiResponse));
            } else {
                logger.debug("Data already available on date : {}", currentDate);
            }
            currentDate = currentDate.minusDays(1);
        }
    }
}
