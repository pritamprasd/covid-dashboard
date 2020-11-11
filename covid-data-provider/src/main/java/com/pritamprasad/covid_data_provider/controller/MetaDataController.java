package com.pritamprasad.covid_data_provider.controller;

import com.pritamprasad.covid_data_provider.models.DateRangeResponse;
import com.pritamprasad.covid_data_provider.service.DateHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pritamprasad.covid_data_provider.util.Messages.CURRENT_DATE_RANGE_FROM_TO_MSG;
import static com.pritamprasad.covid_data_provider.util.Messages.GET_DATERANGE_CALLED_MESSAGE;
import static org.springframework.http.HttpStatus.OK;

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
        logger.debug(GET_DATERANGE_CALLED_MESSAGE);
        final DateRangeResponse dateRangeResponse = dateHandler.getAvailableDateRange();
        logger.debug(CURRENT_DATE_RANGE_FROM_TO_MSG, dateRangeResponse.getMinDate(), dateRangeResponse.getMaxDate());
        return new ResponseEntity<>(dateRangeResponse, OK);
    }

}
