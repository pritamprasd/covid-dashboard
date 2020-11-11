package com.pritamprasad.covid_data_provider.util;

import com.google.gson.Gson;
import com.pritamprasad.covid_data_provider.exception.BadRequestException;
import com.pritamprasad.covid_data_provider.models.LoginRequest;
import com.pritamprasad.covid_data_provider.models.SignupRequest;
import com.pritamprasad.covid_data_provider.models.LocationEntity;
import com.pritamprasad.covid_data_provider.models.LocationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class Validations {

    private static Logger logger = LoggerFactory.getLogger(Validations.class);

    public static List<LocationResponse> sanitized(List<LocationEntity> stateList) {
        return stateList.stream().map(Validations::sanitizeStates).collect(Collectors.toList());
    }

    private static LocationResponse sanitizeStates(final LocationEntity locationEntity) {
        LocationResponse location =  new LocationResponse();
        if(locationEntity.getParentId()!= 0){
            location.setStateId(location.getStateId());
            location.setState(true);
        } else {
            location.setState(false);
        }
        location.setName(locationEntity.getName());
        location.setPopulation(locationEntity.getPopulation());
        return location;
    }

    public static void validateSigninRequest(LoginRequest loginRequest) {
        if(loginRequest == null || loginRequest.getPassword().isEmpty() || loginRequest.getUsername().isEmpty()){
            logger.error("Null property found in request, : "+ new Gson().toJson(loginRequest));
            throw new BadRequestException("username or password not received!");
        }
    }

    public static void validateSignupRequest(SignupRequest signUpRequest) {
        if(signUpRequest == null || signUpRequest.getPassword().isEmpty() || signUpRequest.getUsername().isEmpty() || signUpRequest.getEmail().isEmpty()){
            logger.error("Null property found in request, : "+ new Gson().toJson(signUpRequest));
            throw new BadRequestException("username or password not received!");
        }
    }

}
