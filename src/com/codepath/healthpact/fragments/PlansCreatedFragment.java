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

public class PlansCreatedFragment extends PlanListFragment{

	
	public void getPlans(boolean loadFromDB){
		List<AppPlan> plans = new ArrayList<AppPlan>();	
		plans = AppPlan.getAll(2, ParseUtils.getUserName());
		if(loadFromDB && !plans.isEmpty()){			
			clearProgressBar();
			populatePlans(plans, true);
		}
		populateUserCreatedPlans(plans);
	}
	
	private void populateUserCreatedPlans(final List<AppPlan> appPlansFromDB)	{
		ParseQuery<UserPlan> query = ParseUtils.getPlansCreatedByCurrentUser1();
		
		query.findInBackground(new FindCallback<UserPlan>() {
			@Override
			public void done(List<UserPlan> createdplans, ParseException parseEx) {
				if (parseEx == null) {
					ArrayList<Plan> plans = ParseUtils.getPlansCreatedByCurrentUser();
					List<AppPlan> appPlans = new ArrayList<AppPlan>();
					for(Plan plan: plans){
						boolean planLoaded = false;
						for(AppPlan dbap: appPlansFromDB){
							if(plan.getPlanId().equals(dbap.getPlanid()))
							{
								planLoaded = true;
								break;
							}
						}
						if(!planLoaded){
							AppPlan ap = new AppPlan();
							ap.setAppPlanId(plan.getPlanId()+"__"+ParseUtils.getUserName()+"__2");
							ap.setName(plan.getPlanName());
							ap.setDuration(plan.getPlanDuration());
							ap.setPlanid(plan.getPlanId());
							ap.setCreatedDate(plan.getCreatedAt());
							ap.setUsrName(plan.getCreatedBy());
							ap.setPlantype(2);
							ap.setUser(ParseUtils.getUserName());
							ap.save();
							appPlans.add(ap);
						}
					}
					clearProgressBar();
					populatePlans(appPlans, false);
				}else{
					clearProgressBar();
					Toast.makeText(getActivity(), "Failed to get data", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
