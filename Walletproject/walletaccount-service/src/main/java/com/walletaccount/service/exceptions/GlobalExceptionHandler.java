package com.walletaccount.service.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({
		AlreadyExistException.class,
		NotFoundException.class,
		InvalidCredentialsException.class
	})
	public ResponseEntity<String> handleException(Exception ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}