package com.pritamprasad.covid_data_provider.controller;

import com.pritamprasad.covid_data_provider.security.service.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.pritamprasad.covid_data_provider.util.Messages.*;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private JwtUtils jwtUtils;

    @Autowired
    public AdminController(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    public String sayAdminHello(@AuthenticationPrincipal UserDetails userDetails){
        logger.debug(GET_ADMIN_CALLED);
        return format(HELLO_ADMIN_MESSAGE, userDetails.getUsername());
    }

    @PostMapping("/token")
    public ResponseEntity<String> decodeToken(@RequestBody String token){
        logger.debug(POST_TOKEN_CALLED);
        final long currentTimestamp = currentTimeMillis();
        return new ResponseEntity<>(jwtUtils.decodeToken(token, currentTimestamp), HttpStatus.OK);
    }
}
