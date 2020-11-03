package com.pritamprasad.covid_data_provider.controller;

import com.pritamprasad.covid_data_provider.models.StateResponse;
import com.pritamprasad.covid_data_provider.service.StateHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StateDataController {

    private final Logger logger = LoggerFactory.getLogger(StateDataController.class);

    private StateHandlerService stateHandlerService;

    @Autowired
    public StateDataController(StateHandlerService stateHandlerService) {
        this.stateHandlerService = stateHandlerService;
    }

    @GetMapping("/state")
    public ResponseEntity<List<StateResponse>> getAllStates() {
        List<StateResponse> states = stateHandlerService.getAllStates();
        return new ResponseEntity<>(states, HttpStatus.OK);
    }

    @GetMapping("/state/{code}")
    public ResponseEntity<StateResponse> getStatesByCode(@PathVariable("code") String code) {
        StateResponse states = stateHandlerService.getStateByCode(code);
        return new ResponseEntity<>(states, HttpStatus.OK);
    }
}
