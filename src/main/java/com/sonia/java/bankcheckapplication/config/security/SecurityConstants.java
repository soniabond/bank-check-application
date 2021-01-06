package com.sonia.java.bankcheckapplication.config.security;

public class SecurityConstants {

    private SecurityConstants(){}

    public static final String AUTH_TOKEN_PREFIX = "Bearer ";

    public static final String AUDIENCE_CLAIM_VALUE = "bank-sharing-application";

    public static final String AUTHORITIES_CLAIM = "authorities";
}
