package com.codepath.healthpact.models;

import java.io.Serializable;
import java.util.Date;

public class AppPlan implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Date startDate;
	private Date endDate;
	private int duration;
	private String id;
	private String desc;
	private String usrPlanid;
	private boolean followed;
	
	public AppPlan(){}
	
	public AppPlan(String name, int duration) {
		super();
		this.name = name;
		this.duration = duration;
	}

	public AppPlan(String id, String name, String desc, int duration, Date startDate, Date endDate) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.duration = duration;
		this.startDate = startDate;
		this.endDate = endDate;				
	}
	
	public String getId() {return id;}
	
	public void setId(String id) {this.id = id;}

	public String getName() {return name;}
	
	public void setName(String name) {this.name = name;}

	public Date getStartDate() {return startDate;}

	public Date getEndDate() {return endDate;}

	public int getDuration() {return duration;}

	public void setDuration(int duration) {this.duration = duration;}

	public String getDesc() {return desc;}

	public void setDesc(String desc) {this.desc = desc;}

	public String getUsrPlanid() {return usrPlanid;}

	public void setUsrPlanid(String usrPlanid) {this.usrPlanid = usrPlanid;}

	public boolean getFollowed() {return followed;}

	public void setFollowed(boolean followed) {this.followed = followed;}
}
