package com.pritamprasad.covid_data_provider.controller;

import com.pritamprasad.covid_data_provider.models.DistrictResponse;
import com.pritamprasad.covid_data_provider.models.StateResponse;
import com.pritamprasad.covid_data_provider.service.DistrictHandlerService;
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
public class DistrictDataController {

    private final Logger logger = LoggerFactory.getLogger(DistrictDataController.class);

    private DistrictHandlerService districtHandlerService;

    @Autowired
    public DistrictDataController(DistrictHandlerService districtHandlerService) {
        this.districtHandlerService = districtHandlerService;
    }

    @CrossOrigin("*")
    @GetMapping("/district/")
    public ResponseEntity<List<DistrictResponse>> getAllDistrict(@RequestParam("stateid") int stateName) {
        List<DistrictResponse> districts = districtHandlerService.getAllDistrictByState(stateName);
        return new ResponseEntity<>(districts, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/district/{id}")
    public ResponseEntity<DistrictResponse> getDistrictsByIdOnDate(@PathVariable("id") long districtId, @RequestParam("date") String date) {
        logger.debug("/district/{} called.. date: {}",districtId,date);
        final LocalDate localDate = LocalDate.parse(normalizeDate(date));
        DistrictResponse states = districtHandlerService.getDistrictById(districtId,localDate);
        return new ResponseEntity<>(states, HttpStatus.OK);
    }
}
