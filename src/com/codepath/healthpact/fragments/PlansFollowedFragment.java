package com.codepath.healthpact.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;

import com.codepath.healthpact.adapters.PlanFollowedArrayAdapter;
import com.codepath.healthpact.models.AppPlan;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.models.UserPlan;
import com.codepath.healthpact.parseUtils.ParseUtils;


public class PlansFollowedFragment extends PlanListFragment{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userplanadapter = new PlanFollowedArrayAdapter(getActivity(),appPlans);
	}
	
	public void getPlans(){
		populatePlansFollowed();
	}
	
	public void populatePlansFollowed(){
		ArrayList<UserPlan> userplans = ParseUtils.getUserFollowedPlans(null);
		List<AppPlan> plans = new ArrayList<AppPlan>();
		
		for (UserPlan up : userplans) {
			String plan_id = up.getPlanId();			
			Plan p = ParseUtils.getPlanDetail(null, plan_id);
			Log.d("hp", "up st_date: "+ up.getPlan_start_date());
			if(up.getPlan_start_date()!=null){
				AppPlan ap = new AppPlan(p.getPlanId(), p.getPlanName(), p.getPlanDesc(), p.getPlanDuration(), up.getPlan_start_date(), up.getPlan_end_date());
				ap.setFollowed(true);
				plans.add(ap);
			}
		}
		populatePlans(plans);
	}
}
