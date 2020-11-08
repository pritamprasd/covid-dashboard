package com.pritamprasad.covid_data_provider.controller;

import com.pritamprasad.covid_data_provider.models.DateRangeResponse;
import com.pritamprasad.covid_data_provider.service.DateHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetaDataController {

    private final Logger logger = LoggerFactory.getLogger(StateDataController.class);

    private DateHandlerService dateHandler;

    @Autowired
    public MetaDataController(DateHandlerService dateHandler) {
        this.dateHandler = dateHandler;
    }

    @CrossOrigin("*")
    @GetMapping("/daterange")
    public ResponseEntity<DateRangeResponse> getAllStates() {
        logger.debug("/daterange called..");
        final DateRangeResponse dateRangeResponse = dateHandler.getAvailableDateRange();
        logger.debug("Current date range from {} to {}", dateRangeResponse.getMinDate(), dateRangeResponse.getMaxDate());
        return new ResponseEntity<>(dateRangeResponse, HttpStatus.OK);
    }

}
