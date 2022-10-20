package com.hkprojects.bulletjournal.controllers.utils;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.hkprojects.bulletjournal.entities.dto.UserDTO;
import com.hkprojects.bulletjournal.services.UserService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>{

	@Autowired
	private UserService service;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}
	
	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		UserDTO dto = event.getUserDto();
		String token = UUID.randomUUID().toString();
		service.createVerificationToken(dto, token);
		
		String recipientAddress = dto.getEmail();
		String subject = "Confirmação de registro";
		String confirmationUrl = event.getAppUrl() + "/registration/confirm?token=" + token;
		String message = "Para usufruir do The Bullet Journal realize a confirmação de usuário através do link abaixo: ";
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText(message+ "\r\n"+ confirmationUrl);
		mailSender.send(email);
	}
}
