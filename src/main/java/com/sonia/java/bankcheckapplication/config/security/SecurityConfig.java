package com.sonia.java.bankcheckapplication.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonia.java.bankcheckapplication.config.security.filters.JWTAuthenticationFilter;
import com.sonia.java.bankcheckapplication.config.security.filters.JWTAuthorizationFilter;
import com.sonia.java.bankcheckapplication.config.security.properties.CardCheckingJWTProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableConfigurationProperties(CardCheckingJWTProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final CardCheckingJWTProperties jwtProperties;

    private final ObjectMapper objectMapper;


    public SecurityConfig(@Qualifier("userService") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, CardCheckingJWTProperties jwtProperties, ObjectMapper objectMapper) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProperties = jwtProperties;
        this.objectMapper = objectMapper;
    }

//just a comment
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtProperties, objectMapper))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), securityProperties.getJwt()))
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtProperties))
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
