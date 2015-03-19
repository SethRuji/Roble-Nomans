package edu.rosehulman.exam2sr;

import java.sql.Time;

public class Friend {
	private String name;
	private int hour;
	private int minute;
		
	public Friend() {
		
	}
	
	public Friend(String name, int hour, int minute) {
		this.setName(name);
		this.setHour(hour);
		this.setMinute(minute);
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	
	
}
