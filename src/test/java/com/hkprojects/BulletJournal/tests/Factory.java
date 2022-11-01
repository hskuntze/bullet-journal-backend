package com.hkprojects.BulletJournal.tests;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.hkprojects.bulletjournal.entities.Card;
import com.hkprojects.bulletjournal.entities.Role;
import com.hkprojects.bulletjournal.entities.Streak;
import com.hkprojects.bulletjournal.entities.Todo;
import com.hkprojects.bulletjournal.entities.User;
import com.hkprojects.bulletjournal.entities.dto.CardDTO;
import com.hkprojects.bulletjournal.entities.dto.StreakDTO;
import com.hkprojects.bulletjournal.entities.dto.TodoDTO;
import com.hkprojects.bulletjournal.entities.dto.UserDTO;

public class Factory {
	
	public static User createUser() {
		Set<Role> roles = new HashSet<>();
		roles.add(new Role(1L, "ROLE_VISITOR"));
		return new User(1L, "Alex Brown", "alex@gmail.com", "123456", true, roles);
	}
	
	public static UserDTO createUserDTO() {
		return new UserDTO(createUser());
	}
	
	public static Card createCard() {
		return new Card(1L, "Dia 28/10", "Proin sollicitudin laoreet porttitor. Mauris vestibulum lacus a ornare volutpat. Morbi lobortis.", createUser());
	}
	
	public static CardDTO createCardDTO() {
		return new CardDTO(createCard());
	}
	
	public static Streak createStreak() {
		return new Streak(1L, "Academia", 5, 20, false, 20, "dias", Instant.now(), createUser());
	}
	
	public static StreakDTO createStreakDTO() {
		return new StreakDTO(createStreak());
	}
	
	public static Todo createTodo() {
		return new Todo(1L, "Praticar violão", Instant.now(), false, "medium", createUser());
	}
	
	public static TodoDTO createTodoDTO() {
		return new TodoDTO(createTodo());
	}
}
