package com.pritamprasad.covid_data_provider.security.filters;

import com.pritamprasad.covid_data_provider.security.service.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private JwtUtils jwtUtils;

    private UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Autowired
    public AuthTokenFilter(final JwtUtils jwtUtils, final UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    /**
     * We're anyways going to use {@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter}
     * to authenticate login details, but in order to make UsernamePasswordAuthenticationFilter understand user/pass
     * we need to extract the claims from jwt and create an UsernamePasswordAuthenticationToken and set it to SecurityContext
     * so that when UsernamePasswordAuthenticationFilter run, it'll have an Authentication to validate
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        try {
            final String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.isValidJwt(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else{
                logger.debug("No jwt in request.");
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts token from request
     * @param request HttpServletRequest object
     * @return token without Bearer prefix
     */
    private String parseJwt(HttpServletRequest request) {
        final String headerAuth = request.getHeader("Authorization");
        logger.debug("Authorization Header: {}",headerAuth);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        } else{
            logger.debug("Unrecognized Auth header, doesn't start with Bearer {}", headerAuth);
        }
        return null;
    }
}
