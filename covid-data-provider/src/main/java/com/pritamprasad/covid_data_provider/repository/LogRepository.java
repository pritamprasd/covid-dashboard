package com.pritamprasad.covid_data_provider.repository;

import com.pritamprasad.covid_data_provider.client.LatestLogEndpointResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LogRepository extends JpaRepository<LatestLogEndpointResponse, UUID> {
    LatestLogEndpointResponse findFirstByOrderByTimestampDesc();
}
