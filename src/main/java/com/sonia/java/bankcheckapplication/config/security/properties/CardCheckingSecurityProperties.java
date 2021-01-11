package com.sonia.java.bankcheckapplication.config.security.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@Validated
@ConfigurationProperties(prefix = "card-checking.security")
public class CardCheckingSecurityProperties {

    @Valid
    @NestedConfigurationProperty
    private CardCheckingJWTProperties jwt;

    public CardCheckingJWTProperties getJwt() {
        return jwt;
    }

    public void setJwt(CardCheckingJWTProperties jwt) {
        this.jwt = jwt;
    }
}


