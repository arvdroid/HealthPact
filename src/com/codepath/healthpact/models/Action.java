package com.codepath.healthpact.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Action")
public class Action extends ParseObject {
	private boolean updated;
	private int progress;

	public Action() {
		super();
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public void setActionName(String name) {
		put("action_name", name);
	}

	public String getActionName() {
		return getString("action_name");
	}

	public String getActionServing() {
		return getString("serving");
	}

	public void setActionServing(String serving) {
		put("serving", serving);
	}

	public void setActionDesc(String desc) {
		put("action_desc", desc);
	}

	public String getActionDesc() {
		return getString("action_desc");
	}

	public boolean getUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

}
