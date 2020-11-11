package com.pritamprasad.covid_data_provider.controller;

import com.pritamprasad.covid_data_provider.models.StateResponse;
import com.pritamprasad.covid_data_provider.service.StateHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.pritamprasad.covid_data_provider.util.Helper.normalizeDate;
import static com.pritamprasad.covid_data_provider.util.Messages.GET_STATE_CALLED_COUNTRY_ID;
import static com.pritamprasad.covid_data_provider.util.Messages.GET_STATE_CALLED_START_END;
import static java.time.LocalDate.parse;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class StateDataController {

    private final Logger logger = LoggerFactory.getLogger(StateDataController.class);

    private StateHandlerService stateHandlerService;

    @Autowired
    public StateDataController(StateHandlerService stateHandlerService) {
        this.stateHandlerService = stateHandlerService;
    }

    @CrossOrigin("*")
    @GetMapping("/state")
    public ResponseEntity<List<StateResponse>> getAllStates(
            @RequestParam(name = "countryId", required = false, defaultValue = "0") int countryId) {
        logger.debug(GET_STATE_CALLED_COUNTRY_ID, countryId);
        return new ResponseEntity<>(stateHandlerService.getAllStatesByCountryId(countryId), OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/state/{id}")
    public ResponseEntity<StateResponse> getStatesByCode(@PathVariable("id") long stateId,
                                                         @RequestParam("start") String start,
                                                         @RequestParam("end") String end) {
        logger.debug(GET_STATE_CALLED_START_END, stateId, start, end);
        if (start.equals(end)) {
            final LocalDate parsedDate = parse(normalizeDate(start));
            return new ResponseEntity<>(stateHandlerService.getStateByIdOnDate(stateId, parsedDate), OK);
        } else {
            final StateResponse state = stateHandlerService.getStateDataInBetween(
                    stateId, parse(normalizeDate(start)), parse(normalizeDate(end))
            );
            return new ResponseEntity<>(state, OK);
        }
    }
}
