package com.pritamprasad.covid_data_provider.controller;

import com.pritamprasad.covid_data_provider.models.StateResponse;
import com.pritamprasad.covid_data_provider.service.StateHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.pritamprasad.covid_data_provider.util.Helper.normalizeDate;

@RestController
public class StateDataController {

    /**
     * TODO:
     * 1. /state : should give only list of states, along with districts
     * 2. /state/{code} : should provide info about a state only, no districts data; Optional Time filter
     * 3. /districts/{name} : should provide data about a district; Optional time filter
     */

    private final Logger logger = LoggerFactory.getLogger(StateDataController.class);

    private StateHandlerService stateHandlerService;

    @Autowired
    public StateDataController(StateHandlerService stateHandlerService) {
        this.stateHandlerService = stateHandlerService;
    }

    @CrossOrigin("*")
    @GetMapping("/state")
    public ResponseEntity<List<StateResponse>> getAllStates() {
        logger.debug("/state called..");
        List<StateResponse> states = stateHandlerService.getAllStates();
        return new ResponseEntity<>(states, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/state/{id}")
    public ResponseEntity<StateResponse> getStatesByCode(@PathVariable("id") long stateId, @RequestParam("date") String date) {
        logger.debug("/state/{} called.. date: {}", stateId, date);
        final LocalDate localDate = LocalDate.parse(normalizeDate(date));
        StateResponse state = stateHandlerService.getStateById(stateId, localDate);
        return new ResponseEntity<>(state, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/state/{id}/{start}/{end}")
    public ResponseEntity<StateResponse> getStatesByCode(@PathVariable("id") long stateId,
                                                         @PathVariable("start") String start,
                                                         @PathVariable("end") String end) {
        logger.debug("/state/{} called.. start: {}, end: {}", stateId, start, end);
        final LocalDate startDate = LocalDate.parse(normalizeDate(start));
        final LocalDate endDate = LocalDate.parse(normalizeDate(end));
        StateResponse state = stateHandlerService.getStateDataInBetween(stateId, startDate, endDate);
        return new ResponseEntity<>(state, HttpStatus.OK);
    }
}
