package com.BookStore.demo.exception;

import jakarta.transaction.SystemException;

public class SecurityConfigurationException extends SystemException {

	public SecurityConfigurationException(String message) {
		super("Security Configuration Error: " + message);
	}

}