package com.codepath.healthpact.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.codepath.healthpact.parseUtils.ParseUtils;

public class ActionPerPeriod {
	private LinkedHashMap<Date, ActionPerDay> weekActionDateMap;
	private int currentWeekPointer = 0;
	private boolean[] daysUpdated;
	private WeekRange weekRange;
	
	public ActionPerPeriod() {
		super();
		weekActionDateMap = new LinkedHashMap<Date, ActionPerDay>();
		daysUpdated = new boolean[7];
		this.weekRange = new WeekRange();
	}

	public ActionPerPeriod(LinkedHashMap<Date, ActionPerDay> weekActionDateMap, int currentWeekPointer, boolean[] daysUpdated) {
		super();
		this.weekActionDateMap = weekActionDateMap;
		this.currentWeekPointer = currentWeekPointer;
		this.daysUpdated = daysUpdated;
	}

	public WeekRange getCurrentWeek() {
		return weekRange;
	}
	
	public void addToMap(Date date, boolean flag) {
		if (date == null) {
			return;
		}
		weekRange = new WeekRange();
		ParseUtils.setStartEndDateOfWeek(weekRange, date);
		int dayOfWeek = ParseUtils.getDayOfWeek(date) - 1;

		if (weekActionDateMap.containsKey(weekRange.getStartDate())) {
			ActionPerDay currentVal = weekActionDateMap.get(weekRange.getStartDate());
			currentVal.setDone(dayOfWeek, flag);
		}
		else {
			ActionPerDay apd1 = new ActionPerDay();
			apd1.setDone(dayOfWeek, flag);
			apd1.setWeek(weekRange);
			weekActionDateMap.put(weekRange.getStartDate(), apd1);
		}
	}
	
	public LinkedHashMap<Date, ActionPerDay> getResultSet() {
		return weekActionDateMap;
	}
	
	public boolean[] getDaysUpdated() {
		return daysUpdated;
	}

	public void setDaysUpdated(boolean[] daysUpdated) {
		this.daysUpdated = daysUpdated;
	}
	
	public void setDayStatus(int day, boolean status) {
		this.daysUpdated[day] = status;
	}

	public int getCurrentWeekPointer() {
		return currentWeekPointer;
	}

	public void setCurrentWeekPointer(int currentWeekPointer) {
		this.currentWeekPointer = currentWeekPointer;
	}

	public void setCurrentWeek() {
		ParseUtils.setCurrentWeekStartEndDate(weekRange);
	}

	public class ActionPerDay {
		private boolean[] done;
		private WeekRange week;
		
		
		public ActionPerDay() {
			super();
			this.done = new boolean[7];
		}

		public WeekRange getWeek() {
			return week;
		}
		
		public void setWeek(WeekRange week) {
			this.week = week;
		}

		public boolean isDone(int day) {
			return done[day];
		}
		public void setDone(int day, boolean flag) {
			this.done[day] = flag;
		}
		
		public boolean[] getDaysUpdated() {
			return done;
		}
	}
	
	public class WeekRange {
		private Date startDate;
		private Date endDate;

		public WeekRange() {
			super();
		}

		public WeekRange(Date startDate, Date endDate) {
			this.startDate = startDate;
			this.endDate = endDate;
		}

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
