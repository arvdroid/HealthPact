package com.codepath.healthpact.fragments;

import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

import com.codepath.healthpact.models.AppPlan;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.models.PlanShared;
import com.codepath.healthpact.parseUtils.ParseUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class PlansSharedFragment extends PlanListFragment{
	
	public void getPlans(){
		populateSharedPlans();
	}
	
	public void populateSharedPlans(){
		ParseQuery<PlanShared> query = ParseUtils.getUserSharedPlans();
		
		query.findInBackground(new FindCallback<PlanShared>() {
			@Override
			public void done(List<PlanShared> sharedplans, ParseException parseEx) {
				if (parseEx == null) {
					List<AppPlan> plans = new ArrayList<AppPlan>();					
					for (PlanShared sp : sharedplans) {
						String plan_id = sp.getPlanId();	
						Plan p = ParseUtils.getPlanDetail(null, plan_id);
						AppPlan ap = new AppPlan();
						ap.setName(p.getPlanName());
						ap.setId(p.getPlanId());
						ap.setCreatedDate(p.getCreatedAt());
						ap.setDuration(p.getPlanDuration());
						ap.setUsrPlanid(sp.getUserPlanId());
						ap.setUsrName(p.getCreatedBy());
						plans.add(ap);
					}
					clearProgressBar();
					populatePlans(plans);
				}else{
					clearProgressBar();
					Toast.makeText(getActivity(), "Failed to get data", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}