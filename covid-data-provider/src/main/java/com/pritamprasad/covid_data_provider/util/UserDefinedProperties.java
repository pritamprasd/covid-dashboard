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
@Getter
@Setter
public class UserDefinedProperties {

    private static final Logger logger = LoggerFactory.getLogger(UserDefinedProperties.class);

    @Value("${covid19india.url.latestLog}")
    private String latestLogUrl;

    @Value("${covid19india.url.datewiseSummary}")
    private String datewiseSummaryUrl;

    @Value("${covid19india.pastDataInNumberOfDays}")
    private int pastDataInNumberOfDays;

    @Value("${covid19india.timeTravelMinusDays}")
    private int timeTravelMinusDays;

    @Value("${auth.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.jwtExpirationMs}")
    private int jwtExpirationMs;

    private String adminSessionKey;

    public UserDefinedProperties(){
        adminSessionKey = randomUUID().toString() + randomUUID().toString() + randomUUID().toString();
        logger.info(String.format("Current session admin key: %s", adminSessionKey));
    }
}
