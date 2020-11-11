package com.pritamprasad.covid_data_provider.repository;

import com.pritamprasad.covid_data_provider.models.MetaDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MetadataRepository extends JpaRepository<MetaDataEntity, Long> {

    @Query("Select m From MetaDataEntity m Where m.entityId = ?1 and m.createdDate = ?2")
    Optional<MetaDataEntity> findMetaByEntityIdOnDate(Long entityId, LocalDate date);

    @Query("Select m From MetaDataEntity m Where m.entityId = ?1 and m.createdDate between ?2 and ?3 Order by m.createdDate desc")
    List<MetaDataEntity> findAllByIdBetweenDates(Long entityId, LocalDate start, LocalDate end);

    @Query("Select DISTINCT(m.createdDate) From MetaDataEntity m Order By m.createdDate desc")
    List<LocalDate> finalAllAvailableDatesSortByCreatedDate();

}
