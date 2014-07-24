package com.codepath.healthpact.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "AppPlan")
public class AppPlan extends Model implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(name = "appPlanId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	private String appPlanId;
	
	@Column(name = "name")
	private String name;
	@Column(name = "startDate")
	private Date startDate;
	@Column(name = "endDate")
	private Date endDate;
	@Column(name = "createdDate")
	private Date createdDate;
	@Column(name = "duration")
	private int duration;
	
	@Column(name = "planid")
	private String planid;
	
	@Column(name = "desc")
	private String desc;
	@Column(name = "usrPlanid")
	private String usrPlanid;
	@Column(name = "usrName")
	private String usrName;
	@Column(name = "followed")
	private boolean followed;
	@Column(name = "progress")
	private int progress;
	@Column(name = "plantype")
	private int plantype;
	@Column(name = "user")
	private String user;
	
	public AppPlan(){}
	
	public AppPlan(String name, int duration) {
		super();
		this.name = name;
		this.duration = duration;
	}

	public AppPlan(String appPlanId, String id, String name, String desc, int duration, Date startDate, Date endDate) {
		super();
		this.appPlanId= appPlanId;
		this.planid = id;
		this.name = name;
		this.desc = desc;
		this.duration = duration;
		this.startDate = startDate;
		this.endDate = endDate;				
	}
	
	
	public String getAppPlanId() {return appPlanId;}

	public void setAppPlanId(String appPlanId) {this.appPlanId = appPlanId;}

	public String getPlanid() {return planid;}

	public void setPlanid(String id) {this.planid = id;}

	public String getName() {return name;}
	
	public void setName(String name) {this.name = name;}

	public Date getStartDate() {return startDate;}

	public void setStartDate(Date startDate) {this.startDate = startDate;}

	public Date getEndDate() {return endDate;}
	
	public void setEndDate(Date endDate) {this.endDate = endDate;}

	public Date getCreatedDate() {return createdDate;}

	public void setCreatedDate(Date createdDate) {this.createdDate = createdDate;}

	public int getDuration() {return duration;}

	public void setDuration(int duration) {this.duration = duration;}

	public String getDesc() {return desc;}

	public void setDesc(String desc) {this.desc = desc;}

	public String getUsrPlanid() {return usrPlanid;}

	public void setUsrPlanid(String usrPlanid) {this.usrPlanid = usrPlanid;}

	public boolean getFollowed() {return followed;}

	public void setFollowed(boolean followed) {this.followed = followed;}

	public String getUsrName() {return usrName;}

	public void setUsrName(String usrName) {this.usrName = usrName;}

	public int getProgress() {return progress;}

	public void setProgress(int progress) {this.progress = progress;}
	
	public int getPlantype() {return plantype;}

	public void setPlantype(int plantype) {this.plantype = plantype;}
	
	public String getUser() {return user;}

	public void setUser(String user) {this.user = user;}

	public static List<AppPlan> getAll(int ptype, String loggedInuser) {
	    return new Select()
	        .from(AppPlan.class).where("plantype = ? and user = ?", ptype, loggedInuser).execute();
	}
}
