package com.sonia.java.bankcheckapplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CategoryExceptions {

    public static ResponseStatusException categoryNotFound(String value){
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with name  "+value+" not found");
    }
}
