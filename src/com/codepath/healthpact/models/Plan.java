package com.codepath.healthpact.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Plan")
public class Plan extends ParseObject {

	public Plan() {
		super();
	}

	public void setPlanDesc(String plan_desc) {
		put("plan_desc", plan_desc);
	}

	public String getPlanDesc() {
		return getString("plan_desc");
	}

	public void setPlanDuration(String plan_duration) {
		put("plan_duration", plan_duration);
	}

	public String getPlanDuration() {
		return getString("plan_duration");
	}

	public void setPlanName(String plan_name) {
		put("plan_name", plan_name);
	}

	public String getPlanName() {
		return getString("plan_name");
	}
	
	// Get the user for this item
	public ParseUser getUser() {
		return getParseUser("owner");
	}

	// Associate each item with a user
	public void setOwner(ParseUser user) {
		put("owner", user);
	}
	
	public String getPlanId() {
		return getObjectId();
	}
}
