package com.pritamprasad.covid_data_provider.util;

import com.pritamprasad.covid_data_provider.repository.LocationEntity;
import com.pritamprasad.covid_data_provider.models.LocationResponse;

import java.util.List;
import java.util.stream.Collectors;

public class Validations {

    public static List<LocationResponse> sanitized(List<LocationEntity> stateList) {
        return stateList.stream().map(Validations::sanitizeStates).collect(Collectors.toList());
    }

    private static LocationResponse sanitizeStates(final LocationEntity locationEntity) {
        LocationResponse location =  new LocationResponse();
        if(locationEntity.getStateId() != 0){
            location.setStateId(location.getStateId());
            location.setState(true);
        } else {
            location.setState(false);
        }
        location.setName(locationEntity.getName());
        location.setPopulation(locationEntity.getPopulation());
        return location;
    }
}
