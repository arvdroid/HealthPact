package com.codepath.healthpact.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.Toast;

import com.codepath.healthpact.adapters.PlanFollowedArrayAdapter;
import com.codepath.healthpact.models.AppPlan;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.models.UserPlan;
import com.codepath.healthpact.parseUtils.ParseUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;


public class PlansFollowedFragment extends PlanListFragment{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userplanadapter = new PlanFollowedArrayAdapter(getActivity(),appPlans);
	}
	
	public void getPlans(boolean loadFromDB){
		List<AppPlan> plans = new ArrayList<AppPlan>();	
		plans = AppPlan.getAll(0, ParseUtils.getUserName());
		if(loadFromDB && !plans.isEmpty()){			
			clearProgressBar();
			populatePlans(plans, true);
		}
		populatePlansFollowed(plans);
	}
	
	private void populatePlansFollowed(final List<AppPlan> appPlans){	
		ParseQuery<UserPlan> query = ParseUtils.getUserFollowedPlans();
		query.findInBackground(new FindCallback<UserPlan>() {
			@Override
			public void done(List<UserPlan> userplans, ParseException parseEx) {
				if (parseEx == null) {
					List<AppPlan> plans = new ArrayList<AppPlan>();					
					for (UserPlan up : userplans) {
						String plan_id = up.getPlanId();						
						AppPlan appPlanFromDb = null;
						for(AppPlan dbap: appPlans){
							if(plan_id.equals(dbap.getPlanid()))
							{
								appPlanFromDb = dbap;
								break;
							}
						}
						int progress = (int)ParseUtils.getPlanCompleted(plan_id);
						
						if(appPlanFromDb==null){
							Plan p = ParseUtils.getPlanDetail(null, plan_id);
							if(up.getPlan_start_date()!=null){
								AppPlan ap = new AppPlan(p.getPlanId()+"__"+ParseUtils.getUserName()+"__0", p.getPlanId(), p.getPlanName(), p.getPlanDesc(), p.getPlanDuration(), up.getPlan_start_date(), up.getPlan_end_date());
								ap.setFollowed(true);
								ap.setCreatedDate(p.getCreatedAt());
								ap.setUsrPlanid(up.getObjectId());
								ap.setProgress(progress);
								ap.setUsrName(p.getCreatedBy());
								ap.setStartDate(up.getPlan_start_date());
								ap.setEndDate(up.getPlan_end_date());
								ap.setPlantype(0);
								ap.setUser(ParseUtils.getUserName());
								if (ap.getStartDate() != null) {
									ap.save();
									plans.add(ap);
								}
							}
						} else if(appPlanFromDb!=null && progress!=appPlanFromDb.getProgress()){
							appPlanFromDb.setProgress(progress);
							appPlanFromDb.save();
							updateAdapter(appPlanFromDb);
						}
					}
					clearProgressBar();
					populatePlans(plans, false);
				}else{
					clearProgressBar();
					Toast.makeText(getActivity(), "Failed to get data", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

}
