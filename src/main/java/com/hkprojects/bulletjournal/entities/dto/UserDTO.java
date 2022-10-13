package com.hkprojects.bulletjournal.entities.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.hkprojects.bulletjournal.entities.Role;
import com.hkprojects.bulletjournal.entities.User;

public class UserDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String username;
	private String email;
	
	private Set<RoleDTO> roles = new HashSet<>();
	
	public UserDTO() {
	}

	public UserDTO(Long id, String username, String email) {
		this.id = id;
		this.username = username;
		this.email = email;
	}
	
	public UserDTO(Long id, String username, String email, Set<RoleDTO> roles) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}

	public UserDTO(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.email = user.getEmail();
		for(Role r : user.getRoles()) {
			this.roles.add(new RoleDTO(r));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleDTO> roles) {
		this.roles = roles;
	}
}
