package com.pritamprasad.covid_data_provider.exception;

public class InvalidTokenInHeaderException extends CovidDataProviderException{
    public InvalidTokenInHeaderException(String message) {
        super(message);
    }
}
