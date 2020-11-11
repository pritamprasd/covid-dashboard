package com.pritamprasad.covid_data_provider.controller;

import com.pritamprasad.covid_data_provider.models.DistrictResponse;
import com.pritamprasad.covid_data_provider.service.DistrictHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.pritamprasad.covid_data_provider.util.Helper.normalizeDate;
import static com.pritamprasad.covid_data_provider.util.Messages.GET_DISTRICT_CALLED_START_END;
import static com.pritamprasad.covid_data_provider.util.Messages.GET_DISTRICT_CALLED_STATE_ID;
import static java.time.LocalDate.parse;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class DistrictDataController {

    private final Logger logger = LoggerFactory.getLogger(DistrictDataController.class);

    private DistrictHandlerService districtHandlerService;

    @Autowired
    public DistrictDataController(DistrictHandlerService districtHandlerService) {
        this.districtHandlerService = districtHandlerService;
    }

    @CrossOrigin("*")
    @GetMapping("/district")
    public ResponseEntity<List<DistrictResponse>> getAllDistrictForStateIdWithoutMetadata(@RequestParam("stateid") int stateId) {
        logger.debug(GET_DISTRICT_CALLED_STATE_ID, stateId);
        return new ResponseEntity<>(districtHandlerService.getAllDistrictByStateId(stateId), OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/district/{id}")
    public ResponseEntity<DistrictResponse> getStatesByCode(@PathVariable("id") long districtId,
                                                            @RequestParam("start") String start,
                                                            @RequestParam("end") String end) {
        logger.debug(GET_DISTRICT_CALLED_START_END, districtId, start, end);
        if(start.equals(end)){
            final LocalDate parsedDate = parse(normalizeDate(start));
            return new ResponseEntity<>(districtHandlerService.getDistrictsByIdOnDate(districtId, parsedDate), OK);
        } else{
            final DistrictResponse state = districtHandlerService.getDistrictDataInBetweenDates(
                    districtId, parse(normalizeDate(start)), parse(normalizeDate(end))
            );
            return new ResponseEntity<>(state, OK);
        }
    }
}
