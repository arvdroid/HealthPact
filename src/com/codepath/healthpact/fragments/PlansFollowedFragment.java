package com.codepath.healthpact.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.healthpact.R;
import com.codepath.healthpact.adapters.PlanFollowedArrayAdapter;
import com.codepath.healthpact.models.AppPlan;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.models.UserPlan;
import com.codepath.healthpact.parseUtils.ParseUtils;


public class PlansFollowedFragment extends Fragment{
	
	private ArrayAdapter<AppPlan> userplanadapter;
	private ListView lvUserPlans;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(com.codepath.healthpact.R.layout.fragments_user_plan_list, container, false);
		lvUserPlans = (ListView) v.findViewById(R.id.lvUserPlans);
		lvUserPlans.setAdapter(userplanadapter);
		return v;

	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		

		ArrayList<UserPlan> userplans = ParseUtils.getUserFollowedPlans(null);
		List<AppPlan> plans = new ArrayList<AppPlan>();
		
		for (UserPlan up : userplans) {
			String plan_id = up.getPlanId();
			String usrPlanId = up.getObjectId();
			Plan p = ParseUtils.getPlanDetail(null, plan_id);
			Log.d("hp", "up st_date: "+ up.getPlan_start_date());
			if(up.getPlan_start_date()!=null){
				AppPlan ap = new AppPlan(p.getPlanId(), p.getPlanName(), p.getPlanDesc(), p.getPlanDuration(), up.getPlan_start_date(), up.getPlan_end_date());
				ap.setFollowed(true);
				plans.add(ap);
			}
		}
    	userplanadapter = new PlanFollowedArrayAdapter(getActivity(),plans);
		super.onCreate(savedInstanceState);
	}
	
	
	
	

}
