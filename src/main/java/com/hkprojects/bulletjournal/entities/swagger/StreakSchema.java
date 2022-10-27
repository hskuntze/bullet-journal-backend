package com.hkprojects.bulletjournal.entities.swagger;

public class StreakSchema {
	public String title;
	public Integer count;
	public Integer total;
	public boolean disabled;
	public boolean doneToday;
	
	public StreakSchema(String title, Integer count, Integer total, boolean disabled, boolean doneToday) {
		this.title = title;
		this.count = count;
		this.total = total;
		this.disabled = disabled;
		this.doneToday = doneToday;
	}
}
