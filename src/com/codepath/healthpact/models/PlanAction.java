package com.codepath.healthpact.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("PlanAction")
public class PlanAction extends ParseObject {

	public PlanAction() {
		super();
	}

	public void setPlanId(String id) {
		put("plan_id", id);
	}

	public String getPlanId() {
		return getString("plan_id");
	}
	
	public void setActionId(String id) {
		put("action_id", id);
	}

	public String getActionId() {
		return getString("action_id");
	}

}
