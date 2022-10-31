package com.hkprojects.bulletjournal.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "tb_streak")
public class Streak implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private Integer count;
	private Integer total;
	private boolean disabled;
	private boolean doneToday;
	private Instant createdAt;
	private String label;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public Streak() {
	}

	public Streak(Long id, String title, Integer count, Integer total, boolean disabled, boolean doneToday, String label, User user) {
		this.id = id;
		this.title = title;
		this.count = count;
		this.total = total;
		this.disabled = disabled;
		this.doneToday = doneToday;
		this.label = label;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isDoneToday() {
		return doneToday;
	}

	public void setDoneToday(boolean doneToday) {
		this.doneToday = doneToday;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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
		Streak other = (Streak) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public String toString() {
		return "Streak [id=" + id + ", title=" + title + ", count=" + count + ", total=" + total + ", disabled="
				+ disabled + ", doneToday=" + doneToday + ", createdAt=" + createdAt + ", label=" + label + ", user="
				+ user + "]";
	}

	@PrePersist
	public void createdAt() {
		this.createdAt = Instant.now();
	}
}
