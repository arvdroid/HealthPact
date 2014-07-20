package com.codepath.healthpact.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Action")
public class Action extends ParseObject{
	private int updated;
	
	public Action() {
		super();
	}
	
	public void setActionName(String name) {put("action_name", name);}

	public String getActionName() {return getString("action_name");}

	public int getUpdated() {return updated;}

	public void setUpdated(int updated) {this.updated = updated;}
}
