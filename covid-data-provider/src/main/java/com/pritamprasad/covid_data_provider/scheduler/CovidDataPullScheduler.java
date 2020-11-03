package com.pritamprasad.covid_data_provider.scheduler;

import com.pritamprasad.covid_data_provider.client.Covid19IndiaApiHandler;
import com.pritamprasad.covid_data_provider.client.LatestLogEndpointResponse;
import com.pritamprasad.covid_data_provider.repository.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CovidDataPullScheduler {

    private Covid19IndiaApiHandler covid19IndiaApiHandler;

    private LogRepository logRepository;

    private final Logger logger = LoggerFactory.getLogger(CovidDataPullScheduler.class);

    @Autowired
    public CovidDataPullScheduler(Covid19IndiaApiHandler covid19IndiaApiHandler, LogRepository logRepository) {
        this.covid19IndiaApiHandler = covid19IndiaApiHandler;
        this.logRepository = logRepository;
    }



    //@Scheduled(fixedDelayString =  "${covid19india.fixedDelay}")
    public void refreshLogs() {
        logger.debug("Refreshing logs now...");
        List<LatestLogEndpointResponse> latestLog = covid19IndiaApiHandler.getLatestLog();
        LatestLogEndpointResponse first = logRepository.findFirstByOrderByTimestampDesc();
        if (first != null) {
            for (LatestLogEndpointResponse log : latestLog) {
                if (log.getTimestamp() > first.getTimestamp()) {
                    logger.debug(String.format("Found new logs.. %s", log.toString()));
                    logRepository.save(log);
                }
            }
        } else {
            for (LatestLogEndpointResponse log : latestLog) {
                logger.debug(String.format("Found new logs.. %s", log.toString()));
                logRepository.save(log);
            }
        }
    }
}
