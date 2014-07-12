package com.codepath.healthpact.fragments;


import java.util.ArrayList;

import com.codepath.healthpact.R;
import com.codepath.healthpact.adapters.PlanArrayAdapter;
import com.codepath.healthpact.models.Plan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;




public class PlanListFragment extends Fragment{
	
	/*private ArrayList<Plan> userplans;
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
		
	    userplanadapter = new UserPlanArrayAdapter(getActivity(),userplans);
		super.onCreate(savedInstanceState);
	}
	
	
	public void addAll(ArrayList<Plan> userplans) {
		if(userplans != null) {
			userplanadapter.addAll(userplans);
		}
	}
	
	public void clear() {
		userplans.clear();
	}*/

}
