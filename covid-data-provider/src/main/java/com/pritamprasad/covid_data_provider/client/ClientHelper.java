package com.pritamprasad.covid_data_provider.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static com.pritamprasad.covid_data_provider.util.Constants.*;
import static java.lang.String.valueOf;

public class ClientHelper {

    private ClientHelper() {
    }

    public static URI constructUrlForCovidindia19Api(final LocalDate date, final String templateUri) throws URISyntaxException {
        final String normalizedMonth = valueOf(date.getMonthValue()).length() == 1 ? ZERO_AS_STRING + date.getMonthValue() : valueOf(date.getMonthValue());
        final String normalizedDay = valueOf(date.getDayOfMonth()).length() == 1 ? ZERO_AS_STRING + date.getDayOfMonth() : valueOf(date.getDayOfMonth());
        final String uriString = templateUri.replace(YEAR_ACRONYM, valueOf(date.getYear()))
                .replace(MONTH_ACRONYM, normalizedMonth)
                .replace(DATE_ACRONYM, normalizedDay);
        return new URI(uriString);
    }
}
