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
	
	public String getPlanId() {
		return getString("user_plan_id");
	}
	
	public void setPlan_id(Object plan_id) {
		put("user_plan_id", plan_id);
	}

	public Date getCompletionDate() {
		return getDate("completion_date");
	}
	
	public void setCompletionDate(String date) {
		put("completion_date", date);
	}
	
	public boolean isUpdated () {
		return getBoolean("updated");
	}
	
	public void setUpdated(boolean flag) {
		put("updated", flag);
	}
	

}
