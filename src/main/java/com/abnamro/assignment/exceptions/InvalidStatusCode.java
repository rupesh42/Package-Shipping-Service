package com.abnamro.assignment.exceptions;

public class InvalidStatusCode extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidStatusCode(String message) {
		super(message);
	}
}
