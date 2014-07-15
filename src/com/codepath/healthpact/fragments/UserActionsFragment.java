package com.codepath.healthpact.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.healthpact.R;
import com.codepath.healthpact.adapters.ActionArrayAdapter;
import com.codepath.healthpact.models.Action;
import com.codepath.healthpact.models.AppPlan;
import com.codepath.healthpact.parseUtils.ParseUtils;

public class UserActionsFragment extends Fragment{
	
	private ArrayList<Action> planActions;
	private ArrayAdapter<Action> actionarrayadapter;
	private ListView lvPlanActions;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      //populateActions();
     }
	
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
	
	private void showUpdateAction(AppPlan plan){
		((ActionArrayAdapter)actionarrayadapter).setFollowed(plan.getFollowed());
	}
	
	public void populateActions(AppPlan plan) {		
		showUpdateAction(plan);
		actionarrayadapter.clear();
		String id = plan.getId();
		List<Action> actions = ParseUtils.getActionForPlan(id);
		actionarrayadapter.addAll(actions);
	}
	
	public void disableDetailAction(boolean disable){
		((ActionArrayAdapter)actionarrayadapter).setDisableDetails(disable);
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
	
	public void removeAction(){
		int position = lvPlanActions.getSelectedItemPosition();
		Action a = actionarrayadapter.getItem(position);
		actionarrayadapter.remove(a);
	}
	
	public void test(){
		lvPlanActions.getSelectedItemPosition();
	}
	
}
