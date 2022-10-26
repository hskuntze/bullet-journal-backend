package com.hkprojects.bulletjournal.services.exceptions;

public class AuthErrorException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public AuthErrorException(String msg) {
		super(msg);
	}
}
