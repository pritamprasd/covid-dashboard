package com.pritamprasad.covid_data_provider.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class ErrorResponse {
    @Getter
    @Setter
    private String errorMessage;

    @Getter
    private Long timestamp;

    @Getter
    @Setter
    private String endpoint;

    public ErrorResponse() {
        this.timestamp = System.currentTimeMillis();
    }
}
