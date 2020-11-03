package com.pritamprasad.covid_data_provider.exception;

import lombok.Getter;

public abstract class CovidDataProviderException extends RuntimeException{
    @Getter
    private String exceptionMessage;

    CovidDataProviderException(String message){
        this.exceptionMessage = message;
    }

    @Override
    public String toString() {
        return String.format("ExceptionMessage : %s",exceptionMessage);
    }
}
