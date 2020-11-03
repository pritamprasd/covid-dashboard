package com.pritamprasad.covid_data_provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntityRepository extends JpaRepository<LocationEntity, Integer> {

    @Query("Select e from LocationEntity e Where e.name = ?1")
    Optional<LocationEntity> findByName(String name);

    @Query("Select e from LocationEntity e Where e.code = ?1")
    Optional<LocationEntity> findByCode(String code);
}
