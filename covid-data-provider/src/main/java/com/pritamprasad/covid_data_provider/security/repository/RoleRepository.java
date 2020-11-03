package com.pritamprasad.covid_data_provider.security.repository;

import com.pritamprasad.covid_data_provider.security.models.RoleEntity;
import com.pritamprasad.covid_data_provider.security.models.UserEntity;
import com.pritamprasad.covid_data_provider.security.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByRoleName(UserRole roleName);
}
