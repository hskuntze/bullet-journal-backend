package com.hkprojects.bulletjournal.controllers;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.hkprojects.bulletjournal.controllers.exceptions.InvalidTokenException;
import com.hkprojects.bulletjournal.entities.User;
import com.hkprojects.bulletjournal.entities.VerificationToken;
import com.hkprojects.bulletjournal.entities.dto.UserInsertDTO;
import com.hkprojects.bulletjournal.services.UserService;

@RestController
@RequestMapping(value = "/registration")
public class RegistrationController {
	
	@Autowired
	private UserService service;
	
	@GetMapping(value = "/confirm")
	public String cofirmRegistration(WebRequest request, @RequestParam("token") String token) throws InvalidTokenException {
		
		VerificationToken verificationToken = service.getVerificationToken(token);
		if(verificationToken == null) {
			throw new InvalidTokenException("Token inválido ou inexistente.");
		}
		
		User user = verificationToken.getUser();
		Calendar cal = Calendar.getInstance();
		if((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			throw new InvalidTokenException("Token expirado");
		}
		
		user.setEnabled(true);
		UserInsertDTO dto = new UserInsertDTO();
		dto.setEmail(user.getEmail());
		dto.setEnabled(user.isEnabled());
		dto.setUsername(user.getUsername());
		dto.setPassword(user.getPassword());
		service.saveRegisteredUser(user);
		return "Sucesso na confirmação de registro!";
	}
}
