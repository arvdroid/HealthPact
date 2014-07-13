package com.codepath.healthpact.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Action")
public class Action extends ParseObject {

	public Action() {
		super();
	}
	
	public void setActionName(String name) {
		put("action_name", name);
	}

	public String getActionName() {
		return getString("action_name");
	}

}
