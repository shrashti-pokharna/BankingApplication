package com.example.customer_management_service.exceptions;

public class CustomerAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerAlreadyExistsException() {
		super("Resource Not Found Exception");
	}

	public CustomerAlreadyExistsException(String message) {
		super(message);
	}

}
