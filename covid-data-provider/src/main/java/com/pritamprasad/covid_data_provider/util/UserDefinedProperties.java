package com.pritamprasad.covid_data_provider.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserDefinedProperties {

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
}
