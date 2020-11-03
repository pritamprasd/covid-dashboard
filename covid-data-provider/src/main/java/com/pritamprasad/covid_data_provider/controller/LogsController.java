package com.pritamprasad.covid_data_provider.controller;

import com.pritamprasad.covid_data_provider.client.Covid19IndiaApiHandler;
import com.pritamprasad.covid_data_provider.client.LatestLogEndpointResponse;
import com.pritamprasad.covid_data_provider.repository.LogRepository;
import com.pritamprasad.covid_data_provider.util.UserDefinedProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/logs")
public class LogsController {

    private final LogRepository logRepository;

    @Autowired
    public LogsController(final LogRepository logRepository){
        this.logRepository = logRepository;
    }

    @GetMapping
    public ResponseEntity<List<LatestLogEndpointResponse>> getlatestLogs(){
        return new ResponseEntity<>(Optional.ofNullable(logRepository.findAll()).orElse(Collections.emptyList()) , HttpStatus.OK);
    }
}
