package com.codepath.healthpact.fragments;

import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

import com.codepath.healthpact.models.AppPlan;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.parseUtils.ParseUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class FindPlansFragment extends PlanListFragment{

	
	public void getPlans(){
		//NO default load of plans
	}

	public void populateFindPlans(String searchText){
		showProgressBar();
		ParseQuery<ParseUser> query = ParseUtils.getUserBasedOnExpertise(searchText);
		query.findInBackground(new FindCallback<ParseUser>() {

			@Override
			public void done(List<ParseUser> users, ParseException parseEx) {
				if (parseEx == null) {
					ArrayList<Plan> plans = ParseUtils.getPlansBasedOnUsers(users);
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
				}else{
					clearProgressBar();
					Toast.makeText(getActivity(), "Failed to get data", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
