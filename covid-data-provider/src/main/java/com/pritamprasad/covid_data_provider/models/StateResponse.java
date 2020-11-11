package com.pritamprasad.covid_data_provider.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateResponse extends BaseResponse{

    private String stateCode;

    private Long countryId;

}
