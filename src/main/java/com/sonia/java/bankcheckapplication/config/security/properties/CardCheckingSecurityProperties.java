package com.sonia.java.bankcheckapplication.config.security.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@Validated
@ConfigurationProperties(prefix = "card-checking.security")
@Component
public class CardCheckingSecurityProperties {

    @Valid
    @NestedConfigurationProperty
    private CardCheckingJWTProperties jwt;

    private Map<@NotBlank String, @Valid CardCheckingAdminProperties> admins;

    public CardCheckingJWTProperties getJwt() {
        return jwt;
    }

    public void setJwt(CardCheckingJWTProperties jwt) {
        this.jwt = jwt;
    }

    public Map<String, CardCheckingAdminProperties> getAdmins() {
        return admins;
    }

    public void setAdmins(Map<String, CardCheckingAdminProperties> admins) {
        this.admins = admins;
    }



}
