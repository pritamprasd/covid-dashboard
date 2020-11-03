package com.pritamprasad.covid_data_provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MetadataRepository  extends JpaRepository<MetaDataEntity, Long> {


    @Query("Select m From MetaDataEntity m Where m.entityId = ?1")
    List<MetaDataEntity> findAllMetaByEntityId(int entityId);

}
