package com.codepath.healthpact.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.healthpact.models.AppPlan;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.models.PlanShared;
import com.codepath.healthpact.parseUtils.ParseUtils;

public class PlansSharedFragment extends PlanListFragment{

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v  = super.onCreateView(inflater, container, savedInstanceState);
		populateSharedPlans();
		return v;
	}
	
	public void populateSharedPlans(){
		ArrayList<PlanShared> sharedplans = ParseUtils.getUserSharedPlans(null);
		List<AppPlan> plans = new ArrayList<AppPlan>();
		
		for (PlanShared sp : sharedplans) {
			String plan_id = sp.getPlanId();	
			Plan p = ParseUtils.getPlanDetail(null, plan_id);
			AppPlan ap = new AppPlan();
			ap.setName(p.getPlanName());
			ap.setId(p.getPlanId());
			ap.setCreatedDate(sp.getCreatedAt());
			ap.setDuration(p.getPlanDuration());
			ap.setUsrPlanid(sp.getUserPlanId());
			plans.add(ap);
		}
		populatePlans(plans);
	}
}
