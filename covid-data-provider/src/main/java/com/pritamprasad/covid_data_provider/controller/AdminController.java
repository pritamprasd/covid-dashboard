package com.pritamprasad.covid_data_provider.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String sayAdminHello(@AuthenticationPrincipal UserDetails userDetails){
        return "Hello Admin : "+ userDetails.getUsername();
    }
}
