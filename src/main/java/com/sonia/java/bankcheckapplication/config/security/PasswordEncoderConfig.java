package com.sonia.java.bankcheckapplication.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
public class PasswordEncoderConfig {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());



    @Bean
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

}
