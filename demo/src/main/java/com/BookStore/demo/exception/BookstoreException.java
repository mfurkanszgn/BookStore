package com.BookStore.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookstoreException extends RuntimeException {

	private final ApiError apiError;

	public BookstoreException(HttpStatus status, String message) {
		this.apiError = new ApiError(status, message);
	}

	public ApiError getApiError() {
		return apiError;
	}
}
