package com.codepath.healthpact.fragments;

import java.util.ArrayList;
import java.util.List;

import com.codepath.healthpact.models.AppPlan;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.parseUtils.ParseUtils;

public class FindPlansFragment extends PlanListFragment{

	
	public void getPlans(){
		//populateFindPlans();
	}
	
	public void populateFindPlans(String searchText)	{
		showProgressBar();
		ArrayList<Plan> plans = ParseUtils.getPlansBasedOnExpertise(searchText);
		List<AppPlan> appPlans = new ArrayList<AppPlan>();
		
		for(Plan plan: plans){
			AppPlan ap = new AppPlan();
			ap.setName(plan.getPlanName());
			ap.setDuration(plan.getPlanDuration());
			ap.setId(plan.getPlanId());
			ap.setCreatedDate(plan.getCreatedAt());
			appPlans.add(ap);
		}
		clearProgressBar();
		populatePlans(appPlans);
		
	}
}
