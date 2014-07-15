package com.codepath.healthpact.models;

import java.util.Date;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("UserPlanRelation")
public class UserPlanRelation extends ParseObject {

	public UserPlanRelation() {
		super();
	}

	public Date getActionDate() {
		return getDate("action_date");
	}
	
	public void setActionDate(String date) {
		put("action_date", date);
	}
	
	public String getUserPlanId() {
		return getString("user_plan_id");
	}
	
	public void setUserPlanId(Object plan_id) {
		put("user_plan_id", plan_id);
	}

	public Date getCompletionDate() {
		return getDate("completion_date");
	}
	
	public void setCompletionDate(Date date) {
		put("completion_date", date);
	} 
	
	public String getActionId() {
		return getString("action_id");
	}
	
	public void setActionId(String action_id) {
		put("action_id", action_id);
	}
	
	public boolean isUpdated () {
		return getBoolean("updated");
	}
	
	public void setUpdated(boolean flag) {
		put("updated", flag);
	}
	

}
