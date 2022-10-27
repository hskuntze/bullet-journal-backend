package com.hkprojects.bulletjournal.entities.swagger;

public class TodoSchema {
	public String title;
	public String priority;
	public boolean done;
	
	public TodoSchema(String title, String priority, boolean done) {
		this.title = title;
		this.priority = priority;
		this.done = done;
	}
}
