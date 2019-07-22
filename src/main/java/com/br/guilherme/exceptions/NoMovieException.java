package com.br.guilherme.exceptions;

public class NoMovieException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6629720982879455598L;

	public NoMovieException() {
		super();
	}
	
	public NoMovieException(String message) {
		super(message);
	}
}
