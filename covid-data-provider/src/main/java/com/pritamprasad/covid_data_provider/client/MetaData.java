package com.pritamprasad.covid_data_provider.client;

import lombok.Getter;

@Getter
public class MetaData {
    private Long confirmed;
    private Long deceased;
    private Long recovered;
    private Long tested;
}