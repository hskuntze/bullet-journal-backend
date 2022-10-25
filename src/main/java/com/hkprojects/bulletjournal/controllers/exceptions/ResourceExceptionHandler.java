package com.hkprojects.bulletjournal.controllers.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hkprojects.bulletjournal.services.exceptions.DatabaseException;
import com.hkprojects.bulletjournal.services.exceptions.RestrictedUsernameException;
import com.hkprojects.bulletjournal.services.exceptions.UserAlreadyExistsException;

@ControllerAdvice
public class ResourceExceptionHandler {
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<StandardError> invalidToken(InvalidTokenException e, HttpServletRequest request){
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
		err.setError("Invalid Token");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<StandardError> usernameNotFound(UsernameNotFoundException e, HttpServletRequest request){
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Username not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<StandardError> userAlredyExists(UserAlreadyExistsException e, HttpServletRequest request){
		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.CONFLICT;
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Already exists");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(RestrictedUsernameException.class)
	public ResponseEntity<StandardError> restrictedUsername(RestrictedUsernameException e, HttpServletRequest request){
		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Restricted username");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> databaseException(DatabaseException e, HttpServletRequest request){
		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("DB Exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
 }
