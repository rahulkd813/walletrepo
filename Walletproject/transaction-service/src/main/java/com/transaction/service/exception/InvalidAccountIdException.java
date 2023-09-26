package com.transaction.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidAccountIdException extends RuntimeException {

    public InvalidAccountIdException(String message) {
        super(message);
    }
}
