package com.hkprojects.bulletjournal.entities.dto;

import java.io.Serializable;
import java.time.Instant;

import com.hkprojects.bulletjournal.entities.Card;

public class CardDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private String description;
	private Instant createdAt;
	
	private UserDTO user;
	
	public CardDTO() {
	}

	public CardDTO(Long id, String title, String description, Instant createdAt, UserDTO user) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.createdAt = createdAt;
		this.user = user;
	}
	
	public CardDTO(Card card) {
		id = card.getId();
		title = card.getTitle();
		description = card.getDescription();
		createdAt = card.getCreatedAt();
		user = new UserDTO(card.getUser());
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "CardDTO [id=" + id + ", title=" + title + ", description=" + description + ", createdAt=" + createdAt
				+ ", user=" + user + "]";
	}
}
