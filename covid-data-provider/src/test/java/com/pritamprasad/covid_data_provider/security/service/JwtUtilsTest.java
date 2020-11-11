package com.pritamprasad.covid_data_provider.security.service;

import com.pritamprasad.covid_data_provider.exception.InvalidJwtToken;
import com.pritamprasad.covid_data_provider.security.models.UserDetailsImpl;
import com.pritamprasad.covid_data_provider.util.UserDefinedProperties;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import java.util.Date;

import static com.pritamprasad.covid_data_provider.util.Constants.ENCRYPTION_ALGORITHM;
import static com.pritamprasad.covid_data_provider.util.Helper.formJwtDecodeResponse;
import static com.pritamprasad.covid_data_provider.util.Messages.JWT_DECODE_TOKEN_SEPARATOR;
import static com.pritamprasad.covid_data_provider.utils.DataProvider.getUserDefinedProperties;
import static io.jsonwebtoken.lang.Assert.isTrue;
import static java.lang.System.currentTimeMillis;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtUtilsTest {

    private static final String DUMMY_USERNAME = "user001";

    private JwtUtils jwtUtils;

    private UserDefinedProperties properties;

    @BeforeEach
    void setUp() {
        properties = getUserDefinedProperties();
        jwtUtils = new JwtUtils(properties);
    }

    @Test
    public void decodeTokenWithValidTokenTest() {
        final long currentTimestamp = currentTimeMillis();
        Date expiration = new Date(currentTimestamp + properties.getJwtExpirationMs());
        String validToken = Jwts.builder()
                .setSubject(DUMMY_USERNAME)
                .setIssuedAt(new Date(currentTimestamp))
                .setExpiration(expiration)
                .signWith(ENCRYPTION_ALGORITHM, properties.getJwtSecret())
                .compact();
        Jwt jwt = Jwts.parser().setSigningKey(properties.getJwtSecret()).parse(validToken);
        String[] tokenResponse = formJwtDecodeResponse(jwt, expiration, currentTimestamp)
                .split(JWT_DECODE_TOKEN_SEPARATOR);

        String[] decodeTokenResponse = jwtUtils.decodeToken(validToken, currentTimestamp)
                .split(JWT_DECODE_TOKEN_SEPARATOR);

        isTrue(tokenResponse[0].equals(decodeTokenResponse[0]));
        isTrue(tokenResponse[1].equals(decodeTokenResponse[1]));
    }

    @Test
    public void decodeTokenWithInValidTokenTest() {
        final long currentTimestamp = currentTimeMillis();
        String invalidToken = "garbage-Token-aaaabbbb";
        try {
            jwtUtils.decodeToken(invalidToken, currentTimestamp)
                    .split(JWT_DECODE_TOKEN_SEPARATOR);
            fail("decodeTokenWithInValidTokenTest failed, no exception on bad token");
        } catch (InvalidJwtToken e){
            // Test passes
        }
    }

    @Test
    public void getUserNameFromJwtTokenWithValidTokenTest() {
        final long currentTimestamp = currentTimeMillis();
        Date expiration = new Date(currentTimestamp + properties.getJwtExpirationMs());
        String validToken = Jwts.builder()
                .setSubject(DUMMY_USERNAME)
                .setIssuedAt(new Date(currentTimestamp))
                .setExpiration(expiration)
                .signWith(ENCRYPTION_ALGORITHM, properties.getJwtSecret())
                .compact();
        String userNameFromValidToken = jwtUtils.getUserNameFromJwtToken(validToken);
        isTrue(userNameFromValidToken.equals(DUMMY_USERNAME));
    }

    @Test
    public void  getUserNameFromJwtTokenInValidTokenTest() {
        final long currentTimestamp = currentTimeMillis();
        String invalidToken = "garbage-Token-aaaabbbb";
        try {
            jwtUtils.getUserNameFromJwtToken(invalidToken);
            fail("getUserNameFromJwtTokenInValidTokenTest failed, no exception on bad token");
        } catch (InvalidJwtToken e){
            // Test passes
        }
    }

    @Test
    public void generateJwtTokenSuccessTest(){
        try {
            Authentication authentication = mock(Authentication.class);
            UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
            when(userDetails.getUsername()).thenReturn(DUMMY_USERNAME);
            when(authentication.getPrincipal()).thenReturn(userDetails);
            String generateJwtToken = jwtUtils.generateJwtToken(authentication, currentTimeMillis());
            String generatedUsername = Jwts.parser().setSigningKey(properties.getJwtSecret())
                    .parseClaimsJws(generateJwtToken).getBody().getSubject();
            isTrue(generatedUsername.equals(DUMMY_USERNAME));
        } catch (Exception ex){
            fail("generateJwtTokenSuccessTest failed, message: "+ ex.getMessage());
        }
    }

    @Test
    public void isValidJwtSuccessTest(){
        final long currentTimestamp = currentTimeMillis();
        String generatedJwt = Jwts.builder().setSubject(DUMMY_USERNAME).setIssuedAt(new Date(currentTimestamp))
                .setExpiration(new Date(currentTimestamp + properties.getJwtExpirationMs()))
                .signWith(ENCRYPTION_ALGORITHM, properties.getJwtSecret()).compact();
        boolean isvalidJwt = jwtUtils.isValidJwt(generatedJwt);
        isTrue(isvalidJwt);
    }

    @Test
    public void isValidJwtExpiredJwtExceptionFailureTest(){
        final long currentTimestamp = currentTimeMillis();
        properties.setJwtExpirationMs(0);
        String generatedJwt = Jwts.builder().setSubject(DUMMY_USERNAME).setIssuedAt(new Date(currentTimestamp))
                .setExpiration(new Date(currentTimestamp + properties.getJwtExpirationMs()))
                .signWith(ENCRYPTION_ALGORITHM, properties.getJwtSecret()).compact();
        boolean isvalidJwt = jwtUtils.isValidJwt(generatedJwt);
        isFalse(isvalidJwt);
    }

    @Test
    public void isValidJwtMalformedJwtExceptionFailureTest(){
        final long currentTimestamp = currentTimeMillis();
        properties.setJwtExpirationMs(0);
        String generatedJwt = "garbagejwttoken";
        boolean isvalidJwt = jwtUtils.isValidJwt(generatedJwt);
        isFalse(isvalidJwt);
    }

    @Test
    public void isValidJwtUnsupportedJwtExceptionFailureTest(){
        final long currentTimestamp = currentTimeMillis();
        String generatedJwt = Jwts.builder().setSubject(DUMMY_USERNAME).setIssuedAt(new Date(currentTimestamp))
                .setExpiration(new Date(currentTimestamp + properties.getJwtExpirationMs())).compact();
        boolean isvalidJwt = jwtUtils.isValidJwt(generatedJwt);
        isFalse(isvalidJwt);
    }

    @Test
    public void isValidJwtSignatureExceptionFailureTest() {
        final long currentTimestamp = currentTimeMillis();
        Date expiration = new Date(currentTimestamp + properties.getJwtExpirationMs());
        String validToken = Jwts.builder()
                .setSubject(DUMMY_USERNAME)
                .setIssuedAt(new Date(currentTimestamp))
                .setExpiration(expiration)
                .signWith(ENCRYPTION_ALGORITHM, properties.getJwtSecret())
                .compact();
        String[] split = validToken.split("\\.");
        String signature = split[2];
        System.out.printf("Jwt Signature: %s%n", signature);
        String garbageSignature = signature + "somegarbage";
        split[2] = garbageSignature;
        String jwtWithInvalidSignature = String.join("\\.", split);
        boolean isvalidJwt = jwtUtils.isValidJwt(jwtWithInvalidSignature);
        isFalse(isvalidJwt);
    }





}
