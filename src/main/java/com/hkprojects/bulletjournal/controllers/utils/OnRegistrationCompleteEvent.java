package com.hkprojects.bulletjournal.controllers.utils;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.hkprojects.bulletjournal.entities.dto.UserDTO;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	
	private String appUrl;
	private Locale locale;
	private UserDTO userDto;
	
	public OnRegistrationCompleteEvent(UserDTO userDto, Locale locale, String appUrl) {
		super(userDto);
		
		this.userDto = userDto;
		this.locale = locale;
		this.appUrl = appUrl;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public UserDTO getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDTO userDto) {
		this.userDto = userDto;
	}
}
