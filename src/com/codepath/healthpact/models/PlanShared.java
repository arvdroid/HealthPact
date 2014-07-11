package com.codepath.healthpact.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.codepath.healthpact.models.Plan;

import java.util.Date;

@ParseClassName("PlanShared")
public class PlanShared extends ParseObject {

	public PlanShared() {
		super();
	}

	public String getPlanId() {
		return getString("plan_id");
	}
	
	public void setPlan_id(Object plan_id) {
		put("plan_id", plan_id);
	}

	public String get_shared_to_user_id() {
		return getString("shared_to_user_id");
	}

	public void set_shared_to_user_id(String shared_to_user_id) {
		put("shared_to_user_id", shared_to_user_id);
	}

	public boolean getPlan_following() {
		return getBoolean("plan_following");
	}
	
	public void setPlan_following(boolean plan_following) {
		put("plan_following", plan_following);
	}
	
	public String getUserPlanId() {
		return getObjectId();
	}
}
