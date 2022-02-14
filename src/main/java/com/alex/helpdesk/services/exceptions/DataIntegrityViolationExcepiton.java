package com.alex.helpdesk.services.exceptions;

public class DataIntegrityViolationExcepiton extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataIntegrityViolationExcepiton(String message, Throwable cause) {
		super(message, cause);
	}

	public DataIntegrityViolationExcepiton(String message) {
		super(message);
	}

	
}
