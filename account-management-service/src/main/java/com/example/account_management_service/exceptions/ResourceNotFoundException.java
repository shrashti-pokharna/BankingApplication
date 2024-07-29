package com.example.account_management_service.exceptions;

/*
 * Custom Exception for if account not found or customer not found
 */
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new Custom exception without the specified message.
	 */
	public ResourceNotFoundException() {
		super("Resource Not Found Exception");
	}

	/**
	 * Constructs a new Custom exception with the specified message.
	 * 
	 * @param message the error message
	 */
	public ResourceNotFoundException(String message) {
		super(message);
	}

}
