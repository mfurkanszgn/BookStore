package com.BookStore.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ApiError implements Serializable {
	private HttpStatus status;
	private String message;
}