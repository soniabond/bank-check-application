package com.sonia.java.bankcheckapplication.config.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonia.java.bankcheckapplication.config.security.properties.CardCheckingJWTProperties;
import com.sonia.java.bankcheckapplication.config.security.SecurityConstants;
import com.sonia.java.bankcheckapplication.model.user.UserLoginRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final CardCheckingJWTProperties jwtProperties;

    private final ObjectMapper objectMapper;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, CardCheckingJWTProperties jwtProperties, ObjectMapper objectMapper) {
        this.jwtProperties = jwtProperties;
        this.objectMapper = objectMapper;
        setAuthenticationManager(authenticationManager);
        setUsernameParameter("login");
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        UserLoginRequest credentials;
        try {
            credentials = objectMapper.readValue(req.getInputStream(), UserLoginRequest.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        var authToken = new UsernamePasswordAuthenticationToken(
                credentials.getEmail(),
                credentials.getPassword()
        );
        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        long now = System.currentTimeMillis();
        var principal = (UserDetails) auth.getPrincipal();
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + jwtProperties.getExpireIn()))
                .withArrayClaim(SecurityConstants.AUTHORITIES_CLAIM, principal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toArray(String[]::new))
                .sign(Algorithm.HMAC512(jwtProperties.getSecret().getBytes()));

        res.addHeader(HttpHeaders.AUTHORIZATION, SecurityConstants.AUTH_TOKEN_PREFIX + token);
         }

}
