package com.sonia.java.bankcheckapplication.config.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sonia.java.bankcheckapplication.config.security.properties.CardCheckingJWTProperties;
import com.sonia.java.bankcheckapplication.config.security.SecurityConstants;
import com.sonia.java.bankcheckapplication.model.user.KnownAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final CardCheckingJWTProperties cardCheckingJWTProperties;

    private static final Logger log = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, CardCheckingJWTProperties cardCheckingJWTProperties) {
        super(authenticationManager);
        this.cardCheckingJWTProperties = cardCheckingJWTProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {



        var securityContext = SecurityContextHolder.getContext();

        var authentication = securityContext.getAuthentication();
        // if authenticated by other means, such as JWTAuthenticationFilter
        if (authentication != null && authentication.isAuthenticated()) {
            chain.doFilter(req, res);
            return;
        }

        String header = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith(SecurityConstants.AUTH_TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        String encodedJwt = header.substring(SecurityConstants.AUTH_TOKEN_PREFIX.length());
        authentication = getAuthentication(encodedJwt);

        securityContext.setAuthentication(authentication);
        chain.doFilter(req, res);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(String encodedJwt) {
        DecodedJWT jwt = null;
         try {
             jwt = JWT.require(Algorithm.HMAC512(cardCheckingJWTProperties.getSecret().getBytes()))
                     .withIssuer(cardCheckingJWTProperties.getIssuer())
                     .withAudience(SecurityConstants.AUDIENCE_CLAIM_VALUE)
                     .build()
                     .verify(encodedJwt);
         }catch (Exception e){
             log.debug("Invalid JWT received");
             return null;
         }

        String username = jwt.getSubject();
        Set<KnownAuthority> authorities = jwt.getClaim(SecurityConstants.AUTHORITIES_CLAIM)
                .asList(String.class)
                .stream()
                .map(KnownAuthority::valueOf)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(KnownAuthority.class)));
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }
}
