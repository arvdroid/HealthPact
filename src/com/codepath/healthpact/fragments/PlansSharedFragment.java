package com.codepath.healthpact.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.healthpact.R;
import com.codepath.healthpact.adapters.PlanArrayAdapter;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.models.PlanShared;
import com.codepath.healthpact.parseUtils.ParseUtils;

public class PlansSharedFragment extends PlanListFragment{
	
	
	private ArrayAdapter<Plan> userplanadapter;
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
		

		ArrayList<PlanShared> sharedplans = ParseUtils.getUserSharedPlans(null);
		List<Plan> plans = new ArrayList<Plan>();
		
		for (PlanShared sp : sharedplans) {
			String plan_id = sp.getPlanId();	
			Plan p = ParseUtils.getPlanDetail(null, plan_id);
			plans.add(p);
		}
    	userplanadapter = new PlanArrayAdapter(getActivity(),plans);
		super.onCreate(savedInstanceState);
	}
	

}
