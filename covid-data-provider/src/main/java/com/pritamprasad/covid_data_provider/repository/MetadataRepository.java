package com.pritamprasad.covid_data_provider.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MetadataRepository extends JpaRepository<MetaDataEntity, Long> {


    @Query("Select m From MetaDataEntity m Where m.entityId = ?1")
    List<MetaDataEntity> findAllMetaByEntityId(int entityId);

    @Query("Select m From MetaDataEntity m Where m.entityId = ?1 and m.createdDate = ?2")
    Optional<MetaDataEntity> findLatestMetaByEntityId(Long entityId, LocalDate date);

    @Query("Select m From MetaDataEntity m Order by m.createdDate desc")
    Page<MetaDataEntity> findLatestOverallMetaData(Pageable pageable);

    @Query("Select m From MetaDataEntity m Order by m.createdDate asc")
    Page<MetaDataEntity> findOldestOverallMetaData(Pageable pageable);


}
