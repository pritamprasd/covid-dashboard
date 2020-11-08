package com.pritamprasad.covid_data_provider.controller;

import com.pritamprasad.covid_data_provider.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private JwtUtils jwtUtils;

    public AdminController(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    public String sayAdminHello(@AuthenticationPrincipal UserDetails userDetails){
        return "Hello Admin : "+ userDetails.getUsername();
    }

    @PostMapping("/token")
    public ResponseEntity<String> decodeToken(@RequestBody String token){
        return new ResponseEntity<>(jwtUtils.decodeToken(token), HttpStatus.OK);
    }
}
