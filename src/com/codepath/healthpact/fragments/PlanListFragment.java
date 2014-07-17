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

public abstract class PlanListFragment extends Fragment{
	
	protected ArrayAdapter<AppPlan> userplanadapter;
	private ListView lvUserPlans;
	protected ArrayList<AppPlan> appPlans = new ArrayList<AppPlan>();;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		userplanadapter = new PlanArrayAdapter(getActivity(),appPlans);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(com.codepath.healthpact.R.layout.fragments_user_plan_list, container, false);
		lvUserPlans = (ListView) v.findViewById(R.id.lvUserPlans);
		lvUserPlans.setAdapter(userplanadapter);
		getPlans();
		return v;
	}
	
	public abstract void getPlans();
	
	public void populatePlans(List<AppPlan> plans){
		userplanadapter.clear();
		userplanadapter.addAll(plans);
	}
}
