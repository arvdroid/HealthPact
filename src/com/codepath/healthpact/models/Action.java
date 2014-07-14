package com.codepath.healthpact.models;

import java.io.Serializable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Action")
public class Action extends ParseObject implements Serializable{

	private static final long serialVersionUID = 1L;

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
