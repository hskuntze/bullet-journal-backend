package com.hkprojects.bulletjournal.entities.swagger;

import java.util.HashSet;
import java.util.Set;

public class UserSchema {
	public String username;
	public String email;
	public String password;
	public Set<RoleScheme> roles = new HashSet<>();
	
	public UserSchema(String username, String email, String password, Set<RoleScheme> roles) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
}
