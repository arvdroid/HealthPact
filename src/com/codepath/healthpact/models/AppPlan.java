package com.codepath.healthpact.models;

import java.util.Date;

public class AppPlan {
	String name;
	Date startDate;
	Date endDate;
	int duration;
	public AppPlan(){
		
	}
	
	public AppPlan(String name, int duration) {
		super();
		this.name = name;
		this.duration = duration;
	}

	public AppPlan(String name, Date startDate, Date endDate) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;				
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}	
	
}
