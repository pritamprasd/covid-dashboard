package com.pritamprasad.covid_data_provider.util;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@Component
public class UserDefinedProperties {

    private static final Logger logger = LoggerFactory.getLogger(UserDefinedProperties.class);

    @Value("${covid19india.url.latestLog}")
    @Getter
    private String latestLogUrl;

    @Value("${covid19india.url.datewiseSummary}")
    @Getter
    @Setter
    private String datewiseSummaryUrl;

    @Getter
    @Value("${covid19india.pastDataInNumberOfDays}")
    private int pastDataInNumberOfDays;

    @Getter
    @Value("${covid19india.timeTravelMinusDays}")
    private int timeTravelMinusDays;

    @Getter
    private String adminSessionKey;

    public UserDefinedProperties(){
        adminSessionKey = randomUUID().toString() + randomUUID().toString() + randomUUID().toString();
        logger.info(String.format("Current session admin key: %s", adminSessionKey));
    }
}
