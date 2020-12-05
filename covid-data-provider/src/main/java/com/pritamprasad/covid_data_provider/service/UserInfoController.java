package com.pritamprasad.covid_data_provider.controller;

import com.pritamprasad.covid_data_provider.models.UserInfoResponse;
import com.pritamprasad.covid_data_provider.service.UserDataHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/userinfo")
public class UserInfoController {

    private UserDataHandlerService userDataHandlerService;

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    public UserInfoController(UserDataHandlerService userDataHandlerService) {
        this.userDataHandlerService = userDataHandlerService;
    }

    @GetMapping
    public ResponseEntity<UserInfoResponse> getUserInfoResponse(){
        logger.info("GET /userinfo called...");
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userDataHandlerService.getUserInfo(authentication));
    }
}