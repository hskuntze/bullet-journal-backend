package com.hkprojects.bulletjournal.entities.dto;

import java.io.Serializable;
import java.time.Instant;

import com.hkprojects.bulletjournal.entities.Todo;

public class TodoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private Instant createdAt;
	private boolean done;
	private String priority;
	
	private UserDTO user;
	
	public TodoDTO() {
	}

	public TodoDTO(Long id, String title, Instant createdAt, boolean done, UserDTO user, String priority) {
		this.id = id;
		this.title = title;
		this.createdAt = createdAt;
		this.done = done;
		this.user = user;
		this.priority = priority;
	}
	
	public TodoDTO(Todo todo) {
		id = todo.getId();
		title = todo.getTitle();
		createdAt = todo.getCreatedAt();
		done = todo.isDone();
		priority = todo.getPriority();
		user = new UserDTO(todo.getUser());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "TodoDTO [id=" + id + ", title=" + title + ", createdAt=" + createdAt + ", done=" + done + ", user="
				+ user + "]";
	}
}
