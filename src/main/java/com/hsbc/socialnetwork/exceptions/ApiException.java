package com.hsbc.socialnetwork.exceptions;

/**
 * Creates a standard Api Exception
 */
public class ApiException extends RuntimeException {

	public ApiException(final String message) {
		super(message);
	}
}
