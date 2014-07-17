package com.codepath.healthpact.models;

import java.util.ArrayList;
import java.util.Date;

public class ActionPerPeriod {
	ArrayList<WeekRange> weeks;
	int currentWeekPointer = 0;
	ArrayList<Boolean> daysUpdated;
	
	public ActionPerPeriod() {
		super();
		weeks = new ArrayList<WeekRange>();
		daysUpdated = new ArrayList<Boolean>();
	}

	public ActionPerPeriod(ArrayList<WeekRange> weeks, int currentWeekPointer, ArrayList<Boolean> daysUpdated) {
		super();
		this.weeks = weeks;
		this.currentWeekPointer = currentWeekPointer;
		this.daysUpdated = daysUpdated;
	}

	public ArrayList<WeekRange> getWeeks() {
		return weeks;
	}
	
	public void setWeeks(ArrayList<WeekRange> weeks) {
		this.weeks = weeks;
	}
	
	public ArrayList<Boolean> getDaysUpdated() {
		return daysUpdated;
	}

	public void setDaysUpdated(ArrayList<Boolean> daysUpdated) {
		this.daysUpdated = daysUpdated;
	}

	public int getCurrentWeekPointer() {
		return currentWeekPointer;
	}

	public void setCurrentWeekPointer(int currentWeekPointer) {
		this.currentWeekPointer = currentWeekPointer;
	}

	public class WeekRange {
		private Date startDate;
		private Date endDate;

		public Date getStartDate() {
			return startDate;
		}
		
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		
		public Date getEndDate() {
			return endDate;
		}
		
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}		
	}
}
