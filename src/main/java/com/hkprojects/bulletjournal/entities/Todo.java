package com.hkprojects.bulletjournal.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "tb_todo")
public class Todo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant createdAt;
	private boolean done;
	private String priority;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "streak_id")
	private Streak streak;
	
	public Todo() {
	}

	public Todo(Long id, String title, Instant createdAt, boolean done, String priority) {
		this.id = id;
		this.title = title;
		this.createdAt = createdAt;
		this.done = done;
		this.priority = priority;
	}
	
	public Todo(Long id, String title, Instant createdAt, boolean done, String priority, User user) {
		this.id = id;
		this.title = title;
		this.createdAt = createdAt;
		this.done = done;
		this.priority = priority;
		this.user = user;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Streak getStreak() {
		return streak;
	}

	public void setStreak(Streak streak) {
		this.streak = streak;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Todo other = (Todo) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", title=" + title + ", createdAt=" + createdAt + ", done=" + done + "]";
	}
	
	@PrePersist
	public void persist() {
		this.createdAt = Instant.now();
	}
}
