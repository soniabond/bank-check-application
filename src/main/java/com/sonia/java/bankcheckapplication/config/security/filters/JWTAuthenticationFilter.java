package com.sonia.java.bankcheckapplication.config.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonia.java.bankcheckapplication.model.user.auth.UserLoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
        setAuthenticationManager(authenticationManager);
        setUsernameParameter("email");
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
        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(req, res);
         }

}
