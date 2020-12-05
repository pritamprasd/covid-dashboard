package com.pritamprasad.covid_data_provider.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse {
    private String userName;
    private Long userId;
    private String email;
    private String commaSeparatedRoles;
    private Boolean isActive;
}
