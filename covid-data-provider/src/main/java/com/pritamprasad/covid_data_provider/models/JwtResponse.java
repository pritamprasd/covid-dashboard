package com.pritamprasad.covid_data_provider.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String jwt;
    private Object id;
    private String username;
    private Object email;
    private List<String> roles;
}
