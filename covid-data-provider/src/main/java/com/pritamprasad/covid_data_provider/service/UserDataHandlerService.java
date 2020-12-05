package com.pritamprasad.covid_data_provider.service;

import com.pritamprasad.covid_data_provider.models.UserInfoResponse;
import com.pritamprasad.covid_data_provider.security.models.UserDetailsImpl;
import com.pritamprasad.covid_data_provider.security.models.UserEntity;
import com.pritamprasad.covid_data_provider.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDataHandlerService {

    private UserRepository userRepository;

    @Autowired
    public UserDataHandlerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInfoResponse getUserInfo(final Authentication authentication) {
        final UserInfoResponse userInfoResponse = new UserInfoResponse();
        final UserDetailsImpl userDetails = (UserDetailsImpl) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
        Optional<UserEntity> user = userRepository.findByUsername(userDetails.getUsername());
        if(user.isPresent()){
            userInfoResponse.setUserName(userDetails.getUsername());
            userInfoResponse.setEmail(userDetails.getEmail());
            userInfoResponse.setCommaSeparatedRoles(userDetails.getAuthorities().stream().map(Object::toString).collect(Collectors.joining(",")));
            userInfoResponse.setIsActive(user.get().getIsActive());
        }
        return userInfoResponse;
    }
}
