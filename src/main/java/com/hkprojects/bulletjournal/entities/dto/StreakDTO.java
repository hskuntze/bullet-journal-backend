package com.hkprojects.bulletjournal.entities.dto;

import java.io.Serializable;
import java.time.Instant;

import com.hkprojects.bulletjournal.entities.Streak;

public class StreakDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private Integer count;
	private Integer total;
	private boolean disabled;
	private Integer totalPerLabel;
	private Instant createdAt;
	private String label;
	private Instant last;
	
	private UserDTO user;
	
	public StreakDTO() {
	}

	public StreakDTO(Long id, String title, Integer count, Integer total, boolean disabled, Integer totalPerLabel, Instant createdAt, String label, Instant last, UserDTO user) {
		this.id = id;
		this.title = title;
		this.count = count;
		this.total = total;
		this.disabled = disabled;
		this.totalPerLabel = totalPerLabel;
		this.createdAt = createdAt;
		this.user = user;
		this.label = label;
		this.last = last;
	}
	
	public StreakDTO(Streak streak) {
		id = streak.getId();
		title = streak.getTitle();
		count = streak.getCount();
		total = streak.getTotal();
		disabled = streak.isDisabled();
		user = new UserDTO(streak.getUser());
		createdAt = streak.getCreatedAt();
		label = streak.getLabel();
		last = streak.getLast();
		totalPerLabel = streak.getTotalPerLabel();
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
	
	public Integer getTotalPerLabel() {
		return totalPerLabel;
	}

	public void setTotalPerLabel(Integer totalPerLabel) {
		this.totalPerLabel = totalPerLabel;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
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

	public Instant getLast() {
		return last;
	}

	public void setLast(Instant last) {
		this.last = last;
	}

	@Override
	public String toString() {
		return "StreakDTO [id=" + id + ", title=" + title + ", count=" + count + ", total=" + total + ", disabled="
				+ disabled + ", totalPerLabel=" + totalPerLabel + ", createdAt=" + createdAt + ", label=" + label
				+ ", last=" + last + ", user=" + user + "]";
	}
}
