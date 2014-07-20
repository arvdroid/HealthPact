package com.codepath.healthpact.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.healthpact.adapters.PlanFollowedArrayAdapter;
import com.codepath.healthpact.models.AppPlan;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.models.UserPlan;
import com.codepath.healthpact.parseUtils.ParseUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;


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
		ParseQuery<UserPlan> query = ParseUtils.getUserFollowedPlans();
		query.findInBackground(new FindCallback<UserPlan>() {
			@Override
			public void done(List<UserPlan> userplans, ParseException parseEx) {
				if (parseEx == null) {
					List<AppPlan> plans = new ArrayList<AppPlan>();					
					for (UserPlan up : userplans) {
						String plan_id = up.getPlanId();			
						Plan p = ParseUtils.getPlanDetail(null, plan_id);
						int progress = (int)ParseUtils.getPlanCompleted(plan_id);
						//Log.d("healthpact", "progress: "+ String.valueOf(progress));
						if(up.getPlan_start_date()!=null){
							AppPlan ap = new AppPlan(p.getPlanId(), p.getPlanName(), p.getPlanDesc(), p.getPlanDuration(), up.getPlan_start_date(), up.getPlan_end_date());
							ap.setFollowed(true);
							ap.setCreatedDate(p.getCreatedAt());
							ap.setUsrPlanid(up.getObjectId());
							ap.setProgress(progress);
							ParseUser usr = p.getUser();
							if(usr!=null)
								ap.setUsrName(usr.getUsername());
							plans.add(ap);
						}
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
