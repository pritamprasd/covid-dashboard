package com.pritamprasad.covid_data_provider.exception;

public class InvalidJwtToken extends CovidDataProviderException{
    public InvalidJwtToken(String message) {
        super(message);
    }
}
