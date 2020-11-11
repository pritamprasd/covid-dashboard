package com.pritamprasad.covid_data_provider.util;

public class Messages {

    public static final String STATE_NOT_FOUND_MSG = "State with id: %s not found";
    public static final String INVALID_TOKEN_MESSAGE = "Invalid token : ";
    public static final String JWT_WILL_EXPIRE_IN_MSG = "Will expire in: ";
    public static final String JWT_DECODE_TOKEN_SEPARATOR = "\n----\n";
    public static final String INVALID_JWT_SIGNATURE_MESSAGE = "Invalid JWT signature";
    public static final String INVALID_JWT_TOKEN_MESSAGE = "Invalid JWT token";
    public static final String JWT_TOKEN_IS_EXPIRED_MESSAGE = "JWT token is expired";
    public static final String JWT_TOKEN_IS_UNSUPPORTED_MESSAGE = "JWT token is unsupported";
    public static final String JWT_ERROR_SUFFIX = ": {}";
    public static final String DATE_RANGE_ERROR_MSG = "Could not able to get date range";
    public static final String NO_DISTRICT_FOR_STATE_FOUND_MESSAGE = "No districts found for stateId: %d";
    public static final String NO_DISTRICT_FOUND_MESSAGE = "No district found for id: %d";
    public static final String NO_STATES_FOUND_FOR_COUNTRY_ID = "No states found for countryId: %d";
    public static final String GET_STATE_CALLED_START_END = "GET /state/{} called.. start: {}, end: {}";
    public static final String GET_STATE_CALLED_COUNTRY_ID = "GET /state called.. countryId : {}";
    public static final String GET_DISTRICT_CALLED_STATE_ID = "GET /district called.. stateId: {}";
    public static final String GET_DISTRICT_CALLED_START_END = "GET /district/{} called.. start: {}, end: {}";
    public static final String HELLO_ADMIN_MESSAGE = "Hello Admin : %s";
    public static final String GET_DATERANGE_CALLED_MESSAGE = "GET /daterange called..";
    public static final String CURRENT_DATE_RANGE_FROM_TO_MSG = "Current date range from {} to {}";
    public static final String GET_ADMIN_CALLED = "GET /admin called..";
    public static final String POST_TOKEN_CALLED = "POST /admin/token called.. ";

    private Messages(){}

}
