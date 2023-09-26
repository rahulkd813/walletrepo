package com.transaction.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({
		AuthenticationFailedException.class,
		InvalidAmountException.class,
		NotFoundException.class,
		InvalidAccountIdException.class
	})
	public ResponseEntity<String> handleException(Exception ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
