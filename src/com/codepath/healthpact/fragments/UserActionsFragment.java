package com.codepath.healthpact.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.healthpact.R;
import com.codepath.healthpact.adapters.ActionArrayAdapter;
import com.codepath.healthpact.adapters.PlanArrayAdapter;
import com.codepath.healthpact.models.Plan;

public class UserActionsFragment extends PlanListFragment{
	
	private ArrayList<Plan> userplans;
	private ArrayAdapter<Plan> actionarrayadapter;
	private ListView lvPlanActions;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(com.codepath.healthpact.R.layout.fragments_action_list, container, false);
		lvPlanActions = (ListView) v.findViewById(R.id.lvPlanActions);
		lvPlanActions.setAdapter(actionarrayadapter);
		return v;

	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		
       populateActions();
		
      super.onCreate(savedInstanceState);
     }
	
	public void populateActions() {
        userplans = new ArrayList<Plan>();
		
		Plan userplan1 = new Plan();
		Plan userplan2 = new Plan();
		Plan userplan3 = new Plan();
		
		userplan1.setPlanDesc("Action 1");
		userplan1.setPlanDuration(2);
        userplans.add(userplan1);
        
		userplan2.setPlanDesc("Action 2");
		userplan2.setPlanDuration(3);
        userplans.add(userplan2);
        
		userplan3.setPlanDesc("Action 3");
		userplan3.setPlanDuration(4);
        userplans.add(userplan3);
        
        actionarrayadapter = new ActionArrayAdapter(getActivity(),userplans);
	}
	
}
