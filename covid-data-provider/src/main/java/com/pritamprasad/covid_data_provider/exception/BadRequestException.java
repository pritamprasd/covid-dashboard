package com.pritamprasad.covid_data_provider.exception;

public class BadRequestException extends CovidDataProviderException{
    public BadRequestException(String message) {
        super(message);
    }
}
