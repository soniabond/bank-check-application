package com.sonia.java.bankcheckapplication.config.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonia.java.bankcheckapplication.model.user.KnownAuthority;
import com.sonia.java.bankcheckapplication.model.user.UserLoginRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String AUTHORILIES_CLAIM = "authorities";
    private final AuthenticationManager authenticationManager;

    private final CardCheckingJWTProperties jwtProperties;

    private final ObjectMapper objectMapper;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, CardCheckingJWTProperties jwtProperties, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtProperties = jwtProperties;
        this.objectMapper = objectMapper;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            UserLoginRequest credentials = objectMapper
                    .readValue(req.getInputStream(), UserLoginRequest.class);



            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getEmail(),
                            credentials.getPassword(),
                            Set.of(new SimpleGrantedAuthority(KnownAuthority.ROLE_USER.name()))
                            )
            );
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error process credentials", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth){

        long now = System.currentTimeMillis();
        UserDetails principal = (UserDetails) auth.getPrincipal();
        String token = JWT.create()
                .withSubject(((UserDetails) auth.getPrincipal()).getUsername())
                .withIssuedAt(new Date(now))
                .withNotBefore(new Date(now))
                .withAudience(SecurityConstants.AUDIENCE_CLAIM_VALUE)
                .withExpiresAt(new Date(now + jwtProperties.getExpireIn()))
                .withIssuer(jwtProperties.getIssuer())
                .withKeyId(jwtProperties.getKeyId())
                .withArrayClaim(AUTHORILIES_CLAIM, principal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toArray(String[]::new))
                .sign(HMAC512(jwtProperties.getSecret().getBytes()));
        res.addHeader(HttpHeaders.AUTHORIZATION, com.sonia.java.bankcheckapplication.config.security.SecurityConstants.AUTH_TOKEN_PREFIX + token);
    }

}
