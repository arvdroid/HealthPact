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
import com.codepath.healthpact.adapters.ActionArrayAdapter;
import com.codepath.healthpact.models.Action;

public class UserActionsFragment extends PlanListFragment{
	
	private ArrayList<Action> planActions;
	private ArrayAdapter<Action> actionarrayadapter;
	private ListView lvPlanActions;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(com.codepath.healthpact.R.layout.fragments_action_list, container, false);
		lvPlanActions = (ListView) v.findViewById(R.id.lvPlanActions);
		planActions = new ArrayList<Action>();
		actionarrayadapter = new ActionArrayAdapter(getActivity(),planActions);
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
        /*userplans = new ArrayList<Plan>();
		
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
        userplans.add(userplan3);*/
        
        //actionarrayadapter = new ActionArrayAdapter(getActivity(),userplans);
	}
	
	public void populateAction(Action action){
		actionarrayadapter.add(action);
	}
	
	public List<Action> getActions(){
		List<Action> actions = new ArrayList<Action>();
		for(int i=0; i<actionarrayadapter.getCount(); i++){
			actions.add(actionarrayadapter.getItem(i));
		}
		return actions;
	}
	
}
