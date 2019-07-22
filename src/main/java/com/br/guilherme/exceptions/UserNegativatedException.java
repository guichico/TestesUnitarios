package com.br.guilherme.exceptions;

public class UserNegativatedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2348624627424470941L;

	public UserNegativatedException() {
		super();
	}
	
	public UserNegativatedException(String message) {
		super(message);
	}
}