package com.br.guilherme.exceptions;

public class ServiceUnavaliableException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7109707763985368766L;

	public ServiceUnavaliableException() {
		super();
	}
	
	public ServiceUnavaliableException(String message) {
		super(message);
	}
}
