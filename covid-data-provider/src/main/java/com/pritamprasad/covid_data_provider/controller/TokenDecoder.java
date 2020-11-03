package com.pritamprasad.covid_data_provider.controller;

import com.pritamprasad.covid_data_provider.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TokenDecoder {

    private JwtUtils jwtUtils;

    @Autowired
    public TokenDecoder(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/auth/token")
    public ResponseEntity<String> decodeToken(@RequestBody String token){
        return new ResponseEntity<>(jwtUtils.decodeToken(token), HttpStatus.OK);
    }
}
