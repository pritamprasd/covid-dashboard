package com.pritamprasad.covid_data_provider.util;

import com.google.gson.Gson;
import com.pritamprasad.covid_data_provider.exception.BadRequestException;
import com.pritamprasad.covid_data_provider.models.LoginRequest;
import com.pritamprasad.covid_data_provider.models.SignupRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Validations {

    private static Logger logger = LoggerFactory.getLogger(Validations.class);

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
