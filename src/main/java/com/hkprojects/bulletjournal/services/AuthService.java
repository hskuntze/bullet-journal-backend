package com.hkprojects.bulletjournal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkprojects.bulletjournal.entities.User;
import com.hkprojects.bulletjournal.repositories.UserRepository;
import com.hkprojects.bulletjournal.services.exceptions.ForbiddenException;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepo;
	
	@Transactional(readOnly = true)
	public User authenticated() {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			return userRepo.findByUsername(username);
		} catch(Exception e) {
			throw new UnauthorizedUserException("Usuário inválido " + e.getMessage());
		}
	}
	
	public void validateAdmin(Long userId) {
		User user = authenticated();
		if(!user.getId().equals(userId) && !user.hasRole("ROLE_ADMIN")) {
			throw new ForbiddenException("Operação não autorizada. Você deve ser um administrador.");
		}
	}
}
