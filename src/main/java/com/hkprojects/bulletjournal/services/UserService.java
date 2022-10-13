package com.hkprojects.bulletjournal.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkprojects.bulletjournal.entities.Role;
import com.hkprojects.bulletjournal.entities.User;
import com.hkprojects.bulletjournal.entities.dto.RoleDTO;
import com.hkprojects.bulletjournal.entities.dto.UserDTO;
import com.hkprojects.bulletjournal.entities.dto.UserInsertDTO;
import com.hkprojects.bulletjournal.repositories.RoleRepository;
import com.hkprojects.bulletjournal.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional(readOnly = true)
	public List<UserDTO> findAll(){
		List<User> list = repository.findAll();
		return list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		return new UserDTO(repository.findById(id).get());
	}
	
	@Transactional
	public UserDTO register(UserInsertDTO obj) {
		User user = new User();
		dtoToEntity(obj, user);
		user.setPassword(encoder.encode(obj.getPassword()));
		user = repository.save(user);
		return new UserDTO(user);
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException(username + " not found!!");
		}
		
		return user;
	}
	
	private void dtoToEntity(UserDTO dto, User entity) {
		entity.setEmail(dto.getEmail());
		entity.setUsername(dto.getUsername());
		entity.getRoles().clear();
		for(RoleDTO role : dto.getRoles()) {
			Role r = roleRepo.getOne(role.getId());
			entity.getRoles().add(r);
		}
	}
}
