package com.hkprojects.bulletjournal.entities.dto;

import java.io.Serializable;

import com.hkprojects.bulletjournal.entities.Streak;

public class StreakTodoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	
	public StreakTodoDTO() {
	}
	
	public StreakTodoDTO(Long id, String title) {
		this.id = id;
		this.title = title;
	}

	public StreakTodoDTO(Streak streak) {
		id = streak.getId();
		title = streak.getTitle();
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

	@Override
	public String toString() {
		return "StreakTodoDTO [id=" + id + ", title=" + title + "]";
	}
}
