package com.codepath.healthpact.fragments;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.codepath.healthpact.models.AppPlan;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.parseUtils.ParseUtils;

public class PlansCreatedFragment extends PlanListFragment{

	
	public void getPlans(){
		populateUserCreatedPlans();
	}
	
	private void populateUserCreatedPlans()	{
		ArrayList<Plan> plans = ParseUtils.getPlansCreatedByCurrentUser();
		List<AppPlan> appPlans = new ArrayList<AppPlan>();
		Log.d("hp", "user plans: "+ plans.size());
		for(Plan plan: plans){
			AppPlan ap = new AppPlan();
			ap.setName(plan.getPlanName());
			ap.setDuration(plan.getPlanDuration());
			ap.setId(plan.getPlanId());
			ap.setCreatedDate(plan.getCreatedAt());
			appPlans.add(ap);
		}
		populatePlans(appPlans);	
	}
}
