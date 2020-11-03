package com.pritamprasad.covid_data_provider.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private Set<String> role;
    private String adminKey;
}
