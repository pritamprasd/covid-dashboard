package com.pritamprasad.covid_data_provider.controller;

import com.pritamprasad.covid_data_provider.models.JwtResponse;
import com.pritamprasad.covid_data_provider.models.LoginRequest;
import com.pritamprasad.covid_data_provider.models.SignupRequest;
import com.pritamprasad.covid_data_provider.security.service.JwtUtils;
import com.pritamprasad.covid_data_provider.security.models.UserDetailsImpl;
import com.pritamprasad.covid_data_provider.security.models.RoleEntity;
import com.pritamprasad.covid_data_provider.security.models.UserEntity;
import com.pritamprasad.covid_data_provider.security.models.UserRole;
import com.pritamprasad.covid_data_provider.security.repository.RoleRepository;
import com.pritamprasad.covid_data_provider.security.repository.UserRepository;
import com.pritamprasad.covid_data_provider.util.UserDefinedProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pritamprasad.covid_data_provider.util.Validations.validateSigninRequest;
import static com.pritamprasad.covid_data_provider.util.Validations.validateSignupRequest;
import static java.lang.System.currentTimeMillis;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDefinedProperties properties;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        validateSigninRequest(loginRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication, currentTimeMillis());

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        /**
         * TODO: why send roles outside, why not put that in jwt and use it for validation
         */
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        validateSignupRequest(signUpRequest);
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }
        UserEntity user = new UserEntity(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
        );
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleRepository.findByRoleName(UserRole.USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
        if(properties.getAdminSessionKey().equals(signUpRequest.getAdminKey())){
            roles.add(roleRepository.findByRoleName(UserRole.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody String token){
        return jwtUtils.isValidJwt(token) ? ResponseEntity.ok("Valid Token") : ResponseEntity.status(401).body("Unauthorized!");
    }
}
