package com.pritamprasad.covid_data_provider.util;

import io.jsonwebtoken.SignatureAlgorithm;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

public class Constants {

    public static final SignatureAlgorithm ENCRYPTION_ALGORITHM = HS512;

    private Constants(){}

    public static final String YEAR_ACRONYM = "YYYY";
    public static final String MONTH_ACRONYM = "MM";
    public static final String DATE_ACRONYM = "DD";
    public static final String ZERO_AS_STRING = "0";


}
