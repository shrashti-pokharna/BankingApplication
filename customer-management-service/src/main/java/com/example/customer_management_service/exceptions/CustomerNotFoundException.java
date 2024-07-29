package com.example.customer_management_service.exceptions;

public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException() {
		super("Resource Not Found Exception");
	}

	public CustomerNotFoundException(String message) {
		super(message);
	}

}
