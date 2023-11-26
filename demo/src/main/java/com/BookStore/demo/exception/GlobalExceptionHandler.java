package com.BookStore.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BookstoreException.class)
	@ResponseStatus
	public ResponseEntity<ApiError> handleBookstoreException(BookstoreException ex) {
		ApiError apiError = ex.getApiError();
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<ApiError> handleBookNotFoundException(BookNotFoundException ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}