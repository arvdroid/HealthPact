package com.codepath.healthpact.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.codepath.healthpact.models.Plan;

import java.util.Date;

@ParseClassName("UserPlan")
public class UserPlan  extends ParseObject {

	private String planDesc;
	
	public UserPlan() {
		super();
	}

	public String getPlanDescFromUserPlan() {
		return planDesc;
	}
	
	public void setPlanDescFromPlan(String planName) {
		planDesc = planName;
	}
	
	public void setPlan_end_date(Date plan_end_date) {
		put("plan_end_date", plan_end_date);
	}

	public Date getPlan_end_date() {
		return getDate("plan_end_date");
	}

	public String getPlanId() {
		return getString("plan_id");
	}
	
	public void setPlan_id(Object plan_id) {
		put("plan_id", plan_id);
	}

	public Date getPlan_start_date() {
		return getDate("plan_start_date");
	}

	public void setPlan_start_date(Date plan_start_date) {
		put("plan_start_date", plan_start_date);
	}

	public String getUser_id() {
		return getString("user_id");
	}

	public void setCreatedBy(String created_by) {
		put("created_by", created_by);
	}

	public String getCreatedBy() {
		return getString("created_by");
	}

	public void setUser_id(String user_id) {
		put("user_id", user_id);
	}

	public boolean getPlan_following() {
		return getBoolean("plan_following");
	}
	
	public void setPlan_following(boolean plan_following) {
		put("plan_following", plan_following);
	}
	
	// Get the user for this item
	public ParseUser getUser() {
		return getParseUser("owner");
	}

	// Associate each item with a user
	public void setOwner(ParseUser user) {
		put("owner", user);
	}
	
	public String getUserPlanId() {
		return getObjectId();
	}
}
