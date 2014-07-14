package com.codepath.healthpact.fragments;


import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.healthpact.R;
import com.codepath.healthpact.adapters.PlanArrayAdapter;
import com.codepath.healthpact.models.AppPlan;

public class PlanListFragment extends Fragment{
	
	private ArrayAdapter<AppPlan> userplanadapter;
	private ListView lvUserPlans;
	private ArrayList<AppPlan> appPlans;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appPlans = new ArrayList<AppPlan>();
		userplanadapter = new PlanArrayAdapter(getActivity(),appPlans);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(com.codepath.healthpact.R.layout.fragments_user_plan_list, container, false);
		lvUserPlans = (ListView) v.findViewById(R.id.lvUserPlans);
		lvUserPlans.setAdapter(userplanadapter);
		return v;
	}
	
	
	public void populatePlans(List<AppPlan> plans){
		userplanadapter.clear();
		userplanadapter.addAll(plans);
	}
}
