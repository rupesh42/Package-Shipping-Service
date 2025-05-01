package com.abnamro.assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice(basePackages = "com.abnamro.assignment")
public class GlobalExceptionHandler {

	@ExceptionHandler(ParcelExistsException.class)
	public ResponseEntity<String> handleUnauthorizedAccessException(ParcelExistsException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}

	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<String> handleMethodException(Exception e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}

}
