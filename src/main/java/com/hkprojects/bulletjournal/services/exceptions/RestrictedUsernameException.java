package com.hkprojects.bulletjournal.services.exceptions;

public class RestrictedUsernameException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public RestrictedUsernameException(String msg) {
		super(msg);
	}
}
