package com.sonia.java.bankcheckapplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class CardCheckExceptions {

    public CardCheckExceptions() {
    }

    public static ResponseStatusException authorityNotFound(String value){
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User authority "+value+" not found");
    }
}
