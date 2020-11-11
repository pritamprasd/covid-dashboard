package com.pritamprasad.covid_data_provider.scheduler;

import com.pritamprasad.covid_data_provider.client.CovidApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TODO: Placeholder to update covid data when needed
 */
@Component
public class CovidDataPullScheduler {

    private CovidApi covidApi;

    private final Logger logger = LoggerFactory.getLogger(CovidDataPullScheduler.class);

    @Autowired
    public CovidDataPullScheduler(CovidApi covidApi) {
        this.covidApi = covidApi;
    }

    //@Scheduled(fixedDelayString =  "${covid19india.fixedDelay}")
    public void refreshLogs() {

    }
}
