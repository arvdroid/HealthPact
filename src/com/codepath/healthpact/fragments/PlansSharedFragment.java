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

public class PlansSharedFragment extends PlanListFragment{
	
	public void getPlans(boolean loadFromDB){
		List<AppPlan> plans = new ArrayList<AppPlan>();	
		plans = AppPlan.getAll(1, ParseUtils.getUserName());
		if(loadFromDB && !plans.isEmpty()){			
			clearProgressBar();
			populatePlans(plans, true);
		}
		populateSharedPlans(plans);
	}
	
	public void populateSharedPlans(final List<AppPlan> appPlans){
		ParseQuery<PlanShared> query = ParseUtils.getUserSharedPlans();
		
		query.findInBackground(new FindCallback<PlanShared>() {
			@Override
			public void done(List<PlanShared> sharedplans, ParseException parseEx) {
				if (parseEx == null) {
					List<AppPlan> plans = new ArrayList<AppPlan>();					
					for (PlanShared sp : sharedplans) {
						String plan_id = sp.getPlanId();	
						boolean planLoaded = false;
						for(AppPlan dbap: appPlans){
							if(plan_id.equals(dbap.getPlanid()))
							{
								planLoaded = true;
								break;
							}
						}
						if(!planLoaded){
							Plan p = ParseUtils.getPlanDetail(null, plan_id);
							AppPlan ap = new AppPlan();
							ap.setAppPlanId(p.getPlanId()+"__"+ParseUtils.getUserName()+"__1");
							ap.setName(p.getPlanName());
							ap.setPlanid(p.getPlanId());
							ap.setCreatedDate(p.getCreatedAt());
							ap.setDuration(p.getPlanDuration());
							ap.setUsrPlanid(sp.getUserPlanId());
							ap.setUsrName(p.getCreatedBy());
							ap.setPlantype(1);
							ap.setUser(ParseUtils.getUserName());
							ap.save();
							plans.add(ap);
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