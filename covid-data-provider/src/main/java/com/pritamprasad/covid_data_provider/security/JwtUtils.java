package com.pritamprasad.covid_data_provider.security;

import com.pritamprasad.covid_data_provider.exception.InvalidTokenInHeaderException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${auth.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.jwtExpirationMs}")
    private int jwtExpirationMs;


    public String decodeToken(String token){
        try {
            Jwt jwt = Jwts.parser().setSigningKey(jwtSecret).parse(token);
            Date now = new Date(System.currentTimeMillis());
            Date expire = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
            return jwt.getHeader().toString() + "\n----\n"
                    + jwt.getBody().toString() + "\n----\n"
                    +"Will expire in: "+ Math.abs(now.getTime() - expire.getTime())/1000.0 +" seconds"  ;
        } catch (Exception ex){
            throw new InvalidTokenInHeaderException("Invalid token : "+ token);
        }
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
//                .setClaims(
//                        new DefaultClaims(new HashMap<String, Object>() {{
//                            put("roles", userPrincipal.getAuthorities().stream()
//                                    .map(ga -> ((GrantedAuthority) ga).getAuthority())
//                                    .collect(Collectors.toList())
//                            );
//                        }}
//                        ))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isValidJwt(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
