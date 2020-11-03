package com.pritamprasad.covid_data_provider.security.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class RoleEntity {

    @Id
    @GeneratedValue
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private UserRole roleName;

}
