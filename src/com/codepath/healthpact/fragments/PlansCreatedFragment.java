package com.codepath.healthpact.fragments;

import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

import com.codepath.healthpact.models.AppPlan;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.models.UserPlan;
import com.codepath.healthpact.parseUtils.ParseUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class PlansCreatedFragment extends PlanListFragment{

	
	public void getPlans(){
		populateUserCreatedPlans();
	}
	
	private void populateUserCreatedPlans()	{
		ParseQuery<UserPlan> query = ParseUtils.getPlansCreatedByCurrentUser1();
		
		query.findInBackground(new FindCallback<UserPlan>() {
			@Override
			public void done(List<UserPlan> createdplans, ParseException parseEx) {
				if (parseEx == null) {
					ArrayList<Plan> plans = ParseUtils.getPlansCreatedByCurrentUser();
					List<AppPlan> appPlans = new ArrayList<AppPlan>();
					
					for(Plan plan: plans){
						AppPlan ap = new AppPlan();
						ap.setName(plan.getPlanName());
						ap.setDuration(plan.getPlanDuration());
						ap.setId(plan.getPlanId());
						ap.setCreatedDate(plan.getCreatedAt());
						ParseUser usr = plan.getUser();
						if(usr!=null)
							ap.setUsrName(usr.getUsername());
						appPlans.add(ap);
					}
					clearProgressBar();
					populatePlans(appPlans);
				}else{
					clearProgressBar();
					Toast.makeText(getActivity(), "Failed to get data", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}