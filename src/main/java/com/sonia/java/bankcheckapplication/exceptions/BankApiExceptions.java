package com.sonia.java.bankcheckapplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BankApiExceptions {
    public static ResponseStatusException invalidXTokenException(){
        return new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid xToken received ");
    }
}
