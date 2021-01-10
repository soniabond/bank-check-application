package com.sonia.java.bankcheckapplication.model.user;

import org.springframework.security.core.GrantedAuthority;

public enum KnownAuthority implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
