package com.pritamprasad.covid_data_provider.security.service;

import com.pritamprasad.covid_data_provider.exception.InvalidJwtToken;
import com.pritamprasad.covid_data_provider.security.models.UserDetailsImpl;
import com.pritamprasad.covid_data_provider.util.UserDefinedProperties;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.pritamprasad.covid_data_provider.util.Constants.ENCRYPTION_ALGORITHM;
import static com.pritamprasad.covid_data_provider.util.Helper.formJwtDecodeResponse;
import static com.pritamprasad.covid_data_provider.util.Messages.*;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class JwtUtils {

    private static final Logger logger = getLogger(JwtUtils.class);

    private UserDefinedProperties properties;

    private JwtParser configuredParser;

    @Autowired
    public JwtUtils(final UserDefinedProperties properties) {
        this.properties = properties;
        this.configuredParser = Jwts.parser().setSigningKey(properties.getJwtSecret());
    }

    public String decodeToken(final String token, final Long currentTimestamp) {
        try {
            final Jwt jwt = configuredParser.parse(token);
            final Date expire = configuredParser.parseClaimsJws(token).getBody().getExpiration();
            return formJwtDecodeResponse(jwt, expire, currentTimestamp);
        } catch (JwtException ex) {
            throw new InvalidJwtToken(INVALID_TOKEN_MESSAGE + token);
        }
    }

    public String getUserNameFromJwtToken(final String token) {
        try {
            return configuredParser.parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException ex) {
            throw new InvalidJwtToken(INVALID_TOKEN_MESSAGE + token);
        }
    }

    public String generateJwtToken(final Authentication authentication, final long currentTimestamp) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date(currentTimestamp))
                .setExpiration(new Date(currentTimestamp + properties.getJwtExpirationMs()))
                .signWith(ENCRYPTION_ALGORITHM, properties.getJwtSecret())
                .compact();
    }

    public boolean isValidJwt(final String authToken) {
        try {
            configuredParser.parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error(INVALID_JWT_SIGNATURE_MESSAGE + JWT_ERROR_SUFFIX, e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error(INVALID_JWT_TOKEN_MESSAGE + JWT_ERROR_SUFFIX, e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error(JWT_TOKEN_IS_EXPIRED_MESSAGE + JWT_ERROR_SUFFIX, e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error(JWT_TOKEN_IS_UNSUPPORTED_MESSAGE + JWT_ERROR_SUFFIX, e.getMessage());
        }
        return false;
    }

}
