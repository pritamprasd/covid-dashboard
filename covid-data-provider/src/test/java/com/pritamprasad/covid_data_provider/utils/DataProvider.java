package com.pritamprasad.covid_data_provider.utils;

import com.pritamprasad.covid_data_provider.util.UserDefinedProperties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.UUID.randomUUID;

public class DataProvider {
    public static UserDefinedProperties getUserDefinedProperties(){
        final UserDefinedProperties properties = new UserDefinedProperties();
        properties.setDatewiseSummaryUrl("http://localhost/datewisecovidurl/data-YYYY-MM-DD.min.json");
        properties.setJwtExpirationMs(60000);
        properties.setJwtSecret(randomUUID().toString());
        return properties;
    }

    public static String getSampleResponseForCovidIndia19DateWiseApi() throws IOException {
        String path = "src/test/resources/sample_response_covid19_datewise_api.json";
        return new String(Files.readAllBytes(Paths.get(path)));
    }

}
