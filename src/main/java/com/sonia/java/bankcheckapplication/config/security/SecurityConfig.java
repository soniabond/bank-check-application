package com.sonia.java.bankcheckapplication.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonia.java.bankcheckapplication.config.security.filters.JWTAuthenticationFilter;
import com.sonia.java.bankcheckapplication.config.security.filters.JWTAuthorizationFilter;
import com.sonia.java.bankcheckapplication.config.security.properties.CardCheckingJWTProperties;
import com.sonia.java.bankcheckapplication.config.security.properties.CardCheckingSecurityProperties;
import com.sonia.java.bankcheckapplication.model.user.SaveUserRequest;
import com.sonia.java.bankcheckapplication.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(CardCheckingJWTProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final CardCheckingSecurityProperties securityProperties;

    private final CardCheckingJWTProperties jwtProperties;

    private final ObjectMapper objectMapper;


    public SecurityConfig( UserService userDetailsService,
                           PasswordEncoder passwordEncoder,
                           CardCheckingSecurityProperties securityProperties,
                           CardCheckingJWTProperties jwtProperties,
                           ObjectMapper objectMapper) {
        this.userService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.securityProperties = securityProperties;
        this.jwtProperties = jwtProperties;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        setupDefaultAdmins();
    }

    private void setupDefaultAdmins() {
        List<SaveUserRequest> requests = securityProperties.getAdmins().entrySet().stream()
                .map(entry -> new SaveUserRequest(
                        entry.getValue().getEmail(),
                        entry.getValue().getPassword(),
                        entry.getKey()))
                .peek(admin -> log.info("Default admin found: {} <{}>", admin.getFirstName(), admin.getEmail()))
                .collect(Collectors.toList());
        userService.mergeAdmins(requests);
    }




    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                //.antMatchers(HttpMethod.POST, "/users/admins").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtProperties, objectMapper))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtProperties))
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
