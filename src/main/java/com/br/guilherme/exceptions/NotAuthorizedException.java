package com.br.guilherme.exceptions;

public class NotAuthorizedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6287061167275267877L;

	public NotAuthorizedException() {
		super();
	}
	
	public NotAuthorizedException(String message) {
		super(message);
	}
}