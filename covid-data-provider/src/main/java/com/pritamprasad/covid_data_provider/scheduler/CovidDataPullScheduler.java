package com.pritamprasad.covid_data_provider.scheduler;

import com.pritamprasad.covid_data_provider.client.Covid19IndiaApiHandler;
import com.pritamprasad.covid_data_provider.client.LatestLogEndpointResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Placeholder to update covid data when needed
 */
@Component
public class CovidDataPullScheduler {

    private Covid19IndiaApiHandler covid19IndiaApiHandler;

    private final Logger logger = LoggerFactory.getLogger(CovidDataPullScheduler.class);

    @Autowired
    public CovidDataPullScheduler(Covid19IndiaApiHandler covid19IndiaApiHandler) {
        this.covid19IndiaApiHandler = covid19IndiaApiHandler;
    }


    //@Scheduled(fixedDelayString =  "${covid19india.fixedDelay}")
    public void refreshLogs() {

    }
}
