package com.sonia.java.bankcheckapplication.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonia.java.bankcheckapplication.config.security.filters.JWTAuthenticationFilter;
import com.sonia.java.bankcheckapplication.config.security.filters.JWTAuthorizationFilter;
import com.sonia.java.bankcheckapplication.config.security.properties.CardCheckingJWTProperties;
import com.sonia.java.bankcheckapplication.config.security.properties.CardCheckingSecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableConfigurationProperties(CardCheckingSecurityProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final CardCheckingSecurityProperties securityProperties;

    private final ObjectMapper objectMapper;


    public SecurityConfig(@Qualifier("userService") UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder,
                          CardCheckingSecurityProperties securityProperties,
                          ObjectMapper objectMapper) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.securityProperties = securityProperties;
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
                // open static resources
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // open swagger-ui
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // allow user registration
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                // by default, require authentication
                .anyRequest().authenticated()
                .and()
                // login filter
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), securityProperties.getJwt(), objectMapper))
                // jwt-verification filter
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), securityProperties.getJwt()))
                // for unauthorized requests return 401
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                // allow cross-origin requests for all endpoints
                .cors().configurationSource(corsConfigurationSource())
                .and()
                // we don't need CSRF protection when we use JWT
                // (if you can steal Authorization header value, your can steal X-CSRF as well)
                .csrf().disable()
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    private CorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
