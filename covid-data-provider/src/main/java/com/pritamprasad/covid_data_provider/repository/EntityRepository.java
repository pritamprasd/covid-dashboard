package com.pritamprasad.covid_data_provider.repository;

import com.pritamprasad.covid_data_provider.models.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntityRepository extends JpaRepository<LocationEntity, Long> {

    @Query("Select e from LocationEntity e Where e.name = ?1 and e.parentId = ?2")
    Optional<LocationEntity> findByNameAndParentId(String name, Long parentId);

    @Query("Select e from LocationEntity e Where e.parentId = ?1")
    List<LocationEntity> findAllLocationEntityByParentId(Long id);

}
