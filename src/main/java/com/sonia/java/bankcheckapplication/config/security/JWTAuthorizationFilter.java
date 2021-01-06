package com.sonia.java.bankcheckapplication.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final CardCheckingJWTProperties cardCheckingJWTProperties;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, CardCheckingJWTProperties cardCheckingJWTProperties) {
        super(authenticationManager);
        this.cardCheckingJWTProperties = cardCheckingJWTProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {


        String header = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith(SecurityConstants.AUTH_TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null) {
            return null;
        }
            // parse the token.
        DecodedJWT jwt = JWT.require(Algorithm.HMAC512(cardCheckingJWTProperties.getSecret().getBytes()))
                .withIssuer(cardCheckingJWTProperties.getIssuer())
                .withAudience(SecurityConstants.AUDIENCE_CLAIM_VALUE)
                .build()
                .verify(token.substring(SecurityConstants.AUTH_TOKEN_PREFIX.length()));

        String username = jwt.getSubject();
        Set<GrantedAuthority> authorities = jwt.getClaim(SecurityConstants.AUTHORITIES_CLAIM)
                .asList(String.class)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }
}
