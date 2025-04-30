package com.abnamro.assignment.exceptions;

public class ParcelExistsException extends RuntimeException {

	private static final long serialVersionUID = 7188388328631498763L;

	public ParcelExistsException(String message) {
		super(message);
	}
}