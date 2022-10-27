package com.hkprojects.bulletjournal.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkprojects.bulletjournal.entities.Role;
import com.hkprojects.bulletjournal.entities.User;
import com.hkprojects.bulletjournal.entities.VerificationToken;
import com.hkprojects.bulletjournal.entities.dto.RoleDTO;
import com.hkprojects.bulletjournal.entities.dto.UserDTO;
import com.hkprojects.bulletjournal.entities.dto.UserInsertDTO;
import com.hkprojects.bulletjournal.repositories.UserRepository;
import com.hkprojects.bulletjournal.repositories.VerificationTokenRepository;
import com.hkprojects.bulletjournal.services.exceptions.DatabaseException;
import com.hkprojects.bulletjournal.services.exceptions.ResourceNotFoundException;
import com.hkprojects.bulletjournal.services.exceptions.RestrictedUsernameException;
import com.hkprojects.bulletjournal.services.exceptions.UserAlreadyExistsException;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private VerificationTokenRepository tokenRepo;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Transactional(readOnly = true)
	public List<UserDTO> findAll() {
		List<User> list = repository.findAll();
		return list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> obj = repository.findById(id);
		User user = obj.orElseThrow(() -> new ResourceNotFoundException("Usuário com id "+id+" não foi localizado."));
		return new UserDTO(user);
	}

	@Transactional
	public UserDTO register(UserInsertDTO obj) {
		if(repository.findByEmail(obj.getEmail()) != null || repository.findByUsername(obj.getUsername()) != null) {
			throw new UserAlreadyExistsException("Usuário com este e-mail e/ou nome de usuário já existe!");
		} 
		if(obj.getUsername().toLowerCase().equals("admin")) {
			throw new RestrictedUsernameException("Este nome de usuário é restrito");
		}
		else {
			User user = new User();
			dtoToEntity(obj, user);
			user.setPassword(encoder.encode(obj.getPassword()));
			user = repository.save(user);
			return new UserDTO(user);
		}
	}
	
	@Transactional
	public void createVerificationToken(UserDTO dto, String token) {
		VerificationToken tk = new VerificationToken(token, repository.getOne(dto.getId()));
		tokenRepo.save(tk);
	}
	
	public VerificationToken getVerificationToken(String token) {
		return tokenRepo.findByToken(token);
	}
	
	public void saveRegisteredUser(User user) {
		repository.save(user);
	}

	public void deleteById(Long id) {
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Erro na base de dados");
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Não foi possível localizar um objeto User com id "+id);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException(username + " not found!!");
		}

		return user;
	}

	private void dtoToEntity(UserDTO dto, User entity) {
		entity.setEmail(dto.getEmail());
		entity.setUsername(dto.getUsername());
		entity.getRoles().clear();
		for (RoleDTO obj : dto.getRoles()) {
			RoleDTO aux = roleService.findById(obj.getId());
			Role role = new Role();
			role.setId(aux.getId());
			role.setAuthority(aux.getAuthority());
			entity.getRoles().add(role);
		}
	}
}
