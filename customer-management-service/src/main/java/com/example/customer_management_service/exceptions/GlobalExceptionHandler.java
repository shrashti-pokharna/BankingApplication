package com.example.customer_management_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Globally handle the all exceptions
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ApiResponse> handlerCustomerNotFoundException(CustomerNotFoundException exception) {
		String message = exception.getMessage();
		ApiResponse response = ApiResponse.builder().message(message).success(true).status(HttpStatus.NOT_FOUND)
				.build();

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(CustomerAlreadyExistsException.class)
	public ResponseEntity<ApiResponse> handlerCustomerAlreadyExistsException(CustomerAlreadyExistsException exception) {
		String message = exception.getMessage();
		ApiResponse response = ApiResponse.builder().message(message).success(true).status(HttpStatus.CONFLICT).build();

		return new ResponseEntity<>(response, HttpStatus.CONFLICT);

	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse> handlerIllegalArgumentException(IllegalArgumentException exception) {
		String message = exception.getMessage();
		ApiResponse response = ApiResponse.builder().message(message).success(true).status(HttpStatus.BAD_REQUEST)
				.build();

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handlerGenericExceptions(Exception exception) {
		String message = exception.getMessage();
		ApiResponse response = ApiResponse.builder().message(message).success(true)
				.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
